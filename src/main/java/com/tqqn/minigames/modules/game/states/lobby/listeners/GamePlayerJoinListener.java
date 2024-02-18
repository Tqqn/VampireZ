package com.tqqn.minigames.modules.game.states.lobby.listeners;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.database.events.GamePlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GamePlayerJoinListener implements Listener {


    /**
     * Mostly a debug/test event to test the scoreboard.
     */
    @EventHandler
    public void onJoin(GamePlayerJoinEvent event) {
        VampireZ.getReflectionLayer().sendSideBarScoreboard(event.getPlayer().getPlayer());
    }
}
