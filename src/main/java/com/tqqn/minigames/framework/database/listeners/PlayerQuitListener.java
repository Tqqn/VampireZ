package com.tqqn.minigames.framework.database.listeners;

import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final DatabaseModule databaseModule;
    private final PlayerModule playerModule;
    public PlayerQuitListener(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
        this.playerModule = (PlayerModule) databaseModule.getPlugin().getModuleManager().getModule(PlayerModule.class);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        databaseModule.savePlayer(playerModule.getPlayerModel(event.getPlayer().getUniqueId()));
    }
}
