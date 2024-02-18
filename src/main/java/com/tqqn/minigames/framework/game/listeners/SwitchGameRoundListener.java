package com.tqqn.minigames.framework.game.listeners;

import com.tqqn.minigames.framework.game.events.SwitchGameRoundEvent;
import com.tqqn.minigames.modules.game.GameModule;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class SwitchGameRoundListener implements Listener {

    private final GameModule gameModule;

    public SwitchGameRoundListener(GameModule gameModule) {
        this.gameModule = gameModule;
    }

    /**
     * Handles the custom SwitchGameRoundEvent
     * Sets the current round of the game to the next round specified in the event.
     * Spawns lightning effects randomly around online players' locations.
     * TODO: Send NMS Title - This action is a placeholder for sending a NMS title message.
     */
    @EventHandler
    public void onRoundSwitch(SwitchGameRoundEvent event) {
        if (event.getNextRound() == null) return;
        gameModule.setCurrentRound(event.getNextRound());
        Bukkit.getOnlinePlayers().forEach(player -> {
            gameModule.spawnLightningRandom(player.getLocation(), 2);
            // TODO: Send NMS Title
        });
    }
}
