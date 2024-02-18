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

/**
 * Abstract class representing a module in the VampireZ plugin.
 * Each module can have listeners and commands associated with it.
 */
@Getter
public abstract class AbstractModule {

    private final VampireZ plugin;

    @Setter private Collection<Listener> listeners;
    @Setter private Map<String, CommandExecutor> commands;
    private final String name;

    /**
     * Constructor to initialize a module with specified parameters.
     *
     * @param plugin The VampireZ plugin instance.
     * @param name   The name of the module.
     */
    public AbstractModule(VampireZ plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    /**
     * Abstract method to be implemented to handle module enable logic.
     */
    public abstract void onEnable();

    /**
     * Abstract method to be implemented to handle module disable logic.
     */
    public abstract void onDisable();

    /**
     * Initializes the module by registering listeners and commands.
     * This method is called during module loading.
     */
    protected void init() {
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " is loading...");
        registerListeners();
        registerCommands();
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " finished loading!");
    }

    /**
     * Disables the module by unregistering listeners.
     * This method is called during module unloading.
     */
    protected void disable() {
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " is disabling...");
        unRegisterListeners();
        Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " finished disabling!");
    }

    /**
     * Registers commands associated with the module.
     */
    private void registerCommands() {
        if (commands == null || commands.isEmpty()) return;
        commands.forEach((commandString, command) -> {
            plugin.getCommand(commandString).setExecutor(command);
            Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " has registered command: " + commandString);
        });
    }

    /**
     * Registers listeners associated with the module.
     */
    private void registerListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        listeners.forEach(listener -> {
            pluginManager.registerEvents(listener, plugin);
            Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " has registered listener: " + listener);
        });
    }

    /**
     * Unregisters listeners associated with the module.
     */
    private void unRegisterListeners() {
        if (listeners == null || listeners.isEmpty()) return;
        listeners.forEach(listener -> {
            HandlerList.unregisterAll(listener);
            Bukkit.getLogger().info(getPlugin().getPrefix() + "Module: " + name + " has unregistered listener: " + listener);
        });
    }
}
