package com.tqqn.minigames.modules;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.modules.database.DatabaseModule;

import java.util.LinkedHashMap;

public class ModuleManager {

    private final VampireZ plugin;
    private final LinkedHashMap<Class<? extends AbstractModule>, AbstractModule> modules;

    public ModuleManager(VampireZ plugin) {
        this.plugin = plugin;
        modules = new LinkedHashMap<>();
        modules.put(DatabaseModule.class, new DatabaseModule(plugin));

        registerModules();
    }

    public void disable() {
        unregisterModules();
    }

    private void registerModules() {
        modules.forEach((key, value) -> {
            value.onEnable();
        });
    }

    private void unregisterModules() {
        modules.forEach((key, value) -> {
            value.onDisable();
            modules.remove(key);
        });
    }

    public AbstractModule getModule(Class<? extends AbstractModule> moduleClass) {
        return modules.get(moduleClass);
    }
}
