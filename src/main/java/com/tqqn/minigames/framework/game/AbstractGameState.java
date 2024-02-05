package com.tqqn.minigames.framework.game;

import com.tqqn.minigames.modules.game.GameModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Collection;

public abstract class AbstractGameState {

    @Getter private final GameModule gameModule;
    @Setter private Collection<Listener> listeners;
    @Getter private final String name;

    public AbstractGameState(GameModule gameModule, String name) {
        this.gameModule = gameModule;
        this.name = name;
    }

    public abstract void onEnable();
    public abstract void onDisable();

    protected void init() {
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " is loading...");
        registerListeners();
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " finished loading!");
    }

    protected void disable() {
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " is disabling...");
        unRegisterListeners();
        Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " finished disabling!");
    }

    private void registerListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        PluginManager pluginManager = gameModule.getPlugin().getServer().getPluginManager();
        listeners.forEach(listener -> {
            pluginManager.registerEvents(listener, gameModule.getPlugin());
            Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " has registered listener: " + listener);
        });
    }

    private void unRegisterListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        listeners.forEach(listener -> {
            HandlerList.unregisterAll(listener);
            Bukkit.getLogger().info(gameModule.getPlugin().getPrefix() + "State: " + name + " has unregistered listener: " + listener);
        });
    }
}
