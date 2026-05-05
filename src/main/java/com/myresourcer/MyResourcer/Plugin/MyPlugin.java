package com.myresourcer.MyResourcer.Plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyPlugin implements IPlugin {

    private static final Logger logger = LoggerFactory.getLogger(MyPlugin.class);

    @Override
    public String getName() {
        return "MyPlugin";
    }

    @Override
    public void execute() {
        logger.info("Loading MyPlugin...");
        System.out.println("=== MyPlugin Executed ===");
        System.out.println("This is a plugin for MyResourcer.");
        System.out.println("===========================");
    }
}

