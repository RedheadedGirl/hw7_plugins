package org.example;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {

    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        File pluginDir = new File(pluginRootDirectory);

        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar")
                && file.getName().contains(pluginName));

        Class[] pluginClasses = new Class[jars.length];

        for (int i = 0; i < jars.length; i++) {
            try {
                URL jarURL = jars[i].toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
                pluginClasses[i] = classLoader.loadClass(pluginClassName);
            } catch (MalformedURLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (Class clazz : pluginClasses) {
            try { // запустим плагины
                Plugin instance = (Plugin) clazz.newInstance();
                instance.doUseful();

                // будем всегда возвращать последний, так как в условии ничего об этом не сказано
                if (clazz == pluginClasses[pluginClasses.length-1]) {
                    return instance;
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null; // если плагинов не оказалось
    }
}
