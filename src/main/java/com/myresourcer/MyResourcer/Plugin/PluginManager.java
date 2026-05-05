package com.myresourcer.MyResourcer.Plugin;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class PluginManager {

    private static final Logger logger = LoggerFactory.getLogger(PluginManager.class);
    private final Map<String, IPlugin> plugins = new ConcurrentHashMap<>();

    public void register(String name, IPlugin plugin) {
        plugins.put(name, plugin);
        logger.info("Plugin registered: " + name);
    }

    public IPlugin getPlugin(String name) {
        return plugins.get(name);
    }

    public void executePlugin(String name) {
        IPlugin plugin = plugins.get(name);
        if (plugin != null) {
            plugin.execute();
            logger.info("Plugin executed: " + name);
        }
    }

    public Collection<IPlugin> getAllPlugins() {
        return plugins.values();
    }

    public List<String> getPluginNames() {
        return new ArrayList<>(plugins.keySet());
    }
}

