package com.myresourcer.MyResourcer.Plugin;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.ApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class PluginConfig {

    private static final Logger logger = LoggerFactory.getLogger(PluginConfig.class);

    @Bean
    public ApplicationRunner loadPlugins(PluginManager pluginManager) {
        return args -> {
            logger.info("Loading plugins...");

            // Load MyPlugin
            pluginManager.register("MyPlugin", new MyPlugin());

            logger.info("Plugins loaded: " + pluginManager.getPluginNames());
        };
    }
}

