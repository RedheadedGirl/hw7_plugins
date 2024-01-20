package org.example;

import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassloader extends URLClassLoader {

    public CustomClassloader(URL[] urls) {
        super(urls);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    c = findClass(name);
                } catch (ClassNotFoundException | NoClassDefFoundError notFoundException) {

                }
                if (c == null) {
                    c = super.loadClass(name, false);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
}
