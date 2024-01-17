package org.example;

public class Main {
    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager("plugins");
        // первым параметром можем передать строку, которая должна быть в джарнике,
        // например first - если захотим получить только первый плагин
        pluginManager.load("plugin", "org.example.HelloPlugin");
    }
}