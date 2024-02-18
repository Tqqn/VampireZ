package com.tqqn.minigames.framework.game;

import com.tqqn.minigames.modules.game.GameModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Collection;

/**
 * Represents an abstract game state.
 */
public abstract class AbstractGameState {

    @Getter private final GameModule gameModule;
    @Setter private Collection<Listener> listeners;
    @Getter private final String name;

    /**
     * Constructs a new AbstractGameState object with the specified GameModule and name.
     *
     * @param gameModule The GameModule instance.
     * @param name       The name of the game state.
     */
    public AbstractGameState(GameModule gameModule, String name) {
        this.gameModule = gameModule;
        this.name = name;
    }

    /**
     * Method called when the game state is enabled.
     */
    public abstract void onEnable();

    /**
     * Method called when the game state is disabled.
     */
    public abstract void onDisable();

    /**
     * Initializes the game state.
     * This method registers event listeners.
     */
    protected void init() {
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " is loading...");
        registerListeners();
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " finished loading!");
    }

    /**
     * Disables the game state.
     * This method unregisters event listeners.
     */
    protected void disable() {
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " is disabling...");
        unRegisterListeners();
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " finished disabling!");
    }

    /**
     * Registers event listeners associated with this game state.
     */
    private void registerListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        PluginManager pluginManager = gameModule.getPlugin().getServer().getPluginManager();
        listeners.forEach(listener -> {
            pluginManager.registerEvents(listener, gameModule.getPlugin());
            Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " has registered listener: " + listener);
        });
    }

    /**
     * Unregisters event listeners associated with this game state.
     */
    private void unRegisterListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        listeners.forEach(listener -> {
            HandlerList.unregisterAll(listener);
            Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " has unregistered listener: " + listener);
        });
    }
}
