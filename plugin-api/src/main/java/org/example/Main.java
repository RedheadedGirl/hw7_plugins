package org.example;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main(String[] args) {
        File pluginDir = new File("plugins");

        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));

        Class[] pluginClasses = new Class[jars.length];

        for (int i = 0; i < jars.length; i++) {
            try {
                URL jarURL = jars[i].toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
                pluginClasses[i] = classLoader.loadClass("org.example.HelloPlugin");
            } catch (MalformedURLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (Class clazz : pluginClasses) {
            try {
                Plugin instance = (Plugin) clazz.newInstance();
                instance.doUseful();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}