package com.tqqn.minigames.modules;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.menu.MenuModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.modules.team.TeamModule;

import java.util.LinkedHashMap;

/**
 * Manages the initialization and registration of modules in the plugin.
 */
public final class ModuleManager {

    private final VampireZ plugin;
    private final LinkedHashMap<Class<? extends AbstractModule>, AbstractModule> modules;

    /**
     * Constructs a new ModuleManager instance.
     *
     * @param plugin The main plugin instance.
     */
    public ModuleManager(VampireZ plugin) {
        this.plugin = plugin;
        modules = new LinkedHashMap<>();
    }

    /**
     * Initializes and registers all modules.
     */
    public void init() {
        modules.put(DatabaseModule.class, new DatabaseModule(plugin));
        modules.put(PlayerModule.class, new PlayerModule(plugin));
        modules.put(GameModule.class, new GameModule(plugin));
        modules.put(TeamModule.class, new TeamModule(plugin));
        modules.put(MenuModule.class, new MenuModule(plugin));

        registerModules();
    }

    /**
     * Disables and unregisters all modules.
     */
    public void disable() {
        unregisterModules();
    }

    /**
     * Registers all modules by calling their onEnable() method.
     */
    private void registerModules() {
        modules.forEach((key, value) -> {
            value.onEnable();
        });
    }

    /**
     * Unregisters all modules by calling their onDisable() method and clears the module collection.
     */
    private void unregisterModules() {
        modules.forEach((key, value) -> {
            value.onDisable();
        });
        modules.clear();
    }

    /**
     * Retrieves a module by its class.
     *
     * @param moduleClass The class of the module.
     * @return The module instance, or null if not found.
     */
    public AbstractModule getModule(Class<? extends AbstractModule> moduleClass) {
        return modules.get(moduleClass);
    }
}
