package com.tqqn.minigames.framework.database.listeners;

import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Listener {

    private final DatabaseModule databaseModule;
    public PlayerQuitListener(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        databaseModule.savePlayer(PlayerModule.getPlayerModel(event.getPlayer().getUniqueId()));
    }
}
