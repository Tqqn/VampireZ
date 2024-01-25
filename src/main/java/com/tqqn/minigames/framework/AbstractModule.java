package com.tqqn.minigames.framework;

import com.tqqn.minigames.VampireZ;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Collection;

@Getter
public abstract class AbstractModule {

    private final VampireZ plugin;
    private final Collection<Listener> listeners;
    public AbstractModule(VampireZ plugin, Collection<Listener> listeners) {
        this.plugin = plugin;
        this.listeners = listeners;
    }
    public abstract void onEnable();
    public abstract void onDisable();

    protected void registerListeners() {
        if (listeners.isEmpty()) return;

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }

    protected void unRegisterListeners() {
        listeners.forEach(HandlerList::unregisterAll);
    }
}
