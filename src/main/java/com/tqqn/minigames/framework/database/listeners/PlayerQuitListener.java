package com.tqqn.minigames.framework.database.listeners;

import com.tqqn.minigames.framework.database.models.PlayerModel;
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

    /**
     * Handles the PlayerQuitEvent.
     * This method sets the quit message to an empty string, removes the player's scoreboard if it exists,
     * and saves the player's data to the database.
     *
     * @param event The PlayerQuitEvent to handle.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        PlayerModel playerModel = PlayerModule.getPlayerModel(event.getPlayer().getUniqueId());
        if (playerModel.getCurrentScoreboard() != null) playerModel.getCurrentScoreboard().removeScoreboard(playerModel);
        databaseModule.savePlayer(playerModel);
    }
}
