package com.tqqn.minigames.framework.game.listeners;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.modules.team.teams.Vampires;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        PlayerModel playerModel = PlayerModule.getPlayerModel(event.getPlayer().getUniqueId());
        Bukkit.getLogger().info(playerModel.getCurrentTeam().getSpawn().toString());
        event.setRespawnLocation(playerModel.getCurrentTeam().getSpawn());
        if (playerModel.getCurrentTeam().getClass() != Vampires.class) return;
        playerModel.getPlayer().setAllowFlight(true);
    }
}
