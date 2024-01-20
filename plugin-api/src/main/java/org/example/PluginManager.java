package org.example;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {

    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public void load(String pluginName, String pluginClassName) {
        File pluginDir = new File(pluginRootDirectory);

        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar")
                && file.getName().contains(pluginName));

        Class[] pluginClasses = new Class[jars.length];

        for (int i = 0; i < jars.length; i++) {
            try {
                URL jarURL = jars[i].toURI().toURL();
                // если захотим использовать классы игры, а не пплагинов:
//                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
//                pluginClasses[i] = classLoader.loadClass(pluginClassName);

                // если хотим использовать классы плагинов:
                CustomClassloader classLoader = new CustomClassloader(new URL[]{jarURL});
                pluginClasses[i] = classLoader.loadClass(pluginClassName, true);
            } catch (MalformedURLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (Class clazz : pluginClasses) {
            try { // запустим плагины
                Object instance = clazz.getDeclaredConstructor().newInstance();
                Method method = instance.getClass().getMethod("doUseful");
                method.invoke(instance);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
                     IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
