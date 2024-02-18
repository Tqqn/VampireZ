package com.tqqn.minigames.framework.game.listeners;

import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.GameModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public final class BlockedMCFeaturesListener implements Listener {


    /**
     * Handles the FoodLevelChangeEvent.
     * This listener cancels players that their food level is changing.
     */
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    /**
     * Handles the BlockBreakEvent.
     * This listener cancels players to break blocks.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    /**
     * Handles the BlockPlaceEvent.
     * This listener cancels players from placing blocks.
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    /**
     * Handles the EntitySpawnEvent.
     * This listener prevents unwanted entities from spawning.
     */
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Player) return;
        event.setCancelled(true);
    }

    /**
     * Handles the EntityDamageEvent.
     * This listener prevents players from taking damage during non-active game state.
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (GameModule.getGameState() == GameStates.ACTIVE) return;
        event.setCancelled(true);
    }
}
