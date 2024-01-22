package com.tqqn.minigames.framework.database.listeners;

import com.tqqn.minigames.framework.database.events.GamePlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        event.setKickMessage("You do not exist");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(null));
    }
}
