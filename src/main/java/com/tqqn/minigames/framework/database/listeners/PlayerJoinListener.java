package com.tqqn.minigames.framework.database.listeners;

import com.tqqn.minigames.framework.database.events.GamePlayerJoinEvent;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerModule playerModule;

    public PlayerJoinListener(DatabaseModule databaseModule) {
        this.playerModule = (PlayerModule) databaseModule.getPlugin().getModuleManager().getModule(PlayerModule.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");

        Bukkit.getLogger().info(String.valueOf(PlayerModule.getPlayerModel(event.getPlayer().getUniqueId())));

        playerModule.processLogin(event.getPlayer());

        Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(PlayerModule.getPlayerModel(event.getPlayer().getUniqueId())));
    }
}
