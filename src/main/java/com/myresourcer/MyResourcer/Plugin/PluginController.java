package com.myresourcer.MyResourcer.Plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

// Controller to manage plugin endpoints
@RestController
@RequestMapping("/api/plugins")
public class PluginController {

    @Autowired
    private PluginManager pluginManager;

    @PostMapping("/hello/execute")
    public Map<String, Object> executeHelloPlugin() {
        pluginManager.executePlugin("MyPlugin");
        return Map.of("status", "success", "message", "MyPlugin executed");
    }

    @GetMapping("/list")
    public Map<String, Object> listPlugins() {
        return Map.of(
            "plugins", pluginManager.getPluginNames(),
            "count", pluginManager.getPluginNames().size()
        );
    }
}

