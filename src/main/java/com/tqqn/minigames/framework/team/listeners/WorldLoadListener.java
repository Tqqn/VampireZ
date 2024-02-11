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
    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (!event.getWorld().getName().equals("world"))  return;
        teamModule.registerTeams();
    }
}
