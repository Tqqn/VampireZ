package com.tqqn.minigames.framework;

import com.tqqn.minigames.VampireZ;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Collection;
import java.util.Map;

@Getter
public abstract class AbstractModule {

    private final VampireZ plugin;

    @Setter private Collection<Listener> listeners;
    @Setter private Map<String, CommandExecutor> commands;
    private final String name;
    public AbstractModule(VampireZ plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }
    public abstract void onEnable();
    public abstract void onDisable();

    protected void init() {
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " is loading...");
        registerListeners();
        registerCommands();
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " finished loading!");
    }

    protected void disable() {
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " is disabling...");
        unRegisterListeners();
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " finished disabling!");
    }

    private void registerCommands() {
        if (commands == null || commands.isEmpty()) return;
        commands.forEach((commandString, command) -> {
            plugin.getCommand(commandString).setExecutor(command);
            Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " has registered command: " + commandString);
        });
    }

    private void registerListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        listeners.forEach(listener -> {
            pluginManager.registerEvents(listener, plugin);
            Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " has registered listener: " + listener);
        });
    }

    private void unRegisterListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        listeners.forEach(listener -> {
            HandlerList.unregisterAll(listener);
            Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " has unregistered listener: " + listener);
        });
    }
}
