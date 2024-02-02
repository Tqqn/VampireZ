package com.tqqn.minigames.modules;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.player.PlayerModule;

import java.util.LinkedHashMap;

public class ModuleManager {

    private final VampireZ plugin;
    private final LinkedHashMap<Class<? extends AbstractModule>, AbstractModule> modules;

    public ModuleManager(VampireZ plugin) {
        this.plugin = plugin;
        modules = new LinkedHashMap<>();
    }

    public void init() {
        modules.put(DatabaseModule.class, new DatabaseModule(plugin));
        modules.put(PlayerModule.class, new PlayerModule(plugin));
        modules.put(GameModule.class, new GameModule(plugin));

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
        });
        modules.clear();
    }

    public AbstractModule getModule(Class<? extends AbstractModule> moduleClass) {
        return modules.get(moduleClass);
    }
}
