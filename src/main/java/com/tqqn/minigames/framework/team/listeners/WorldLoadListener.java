package com.tqqn.minigames.framework.team.listeners;

import com.tqqn.minigames.modules.team.TeamModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener {

    private final TeamModule teamModule;

    public WorldLoadListener(TeamModule teamModule) {
        this.teamModule = teamModule;
    }

    /**
     * Handles the WorldLoadEvent
     * It checks if the loaded world is the main world (named "world").
     * If it's not the main world, the method returns without further action.
     * If it is the main world, it registers teams using the teamModule.
     */
    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (!event.getWorld().getName().equals("world"))  return;
        teamModule.registerTeams();
    }
}
