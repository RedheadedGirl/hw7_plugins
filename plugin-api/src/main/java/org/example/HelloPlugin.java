package org.example;

public class HelloPlugin implements Plugin {
    @Override
    public void doUseful() {
        System.out.println("Plugin of a game! Should be overwritten by plugins from outside!");
    }
}
