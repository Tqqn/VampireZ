package com.tqqn.minigames.framework.team.listeners;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public final class DamageByEntityListener implements Listener {


    /**
     * Handles the EntityDamageByEntityEvent
     * It checks if both the damager and the entity being damaged are instances of Player. If not, the method returns without further action.
     * If both entities are players, it retrieves their PlayerModel objects using PlayerModule.getPlayerModel().
     * Then, it checks if both the attacker and the defender are assigned to a team.
     * If both players belong to the same team, the event is cancelled and a message is sent to the attacker indicating they cannot harm their teammate.
     */
    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) && !(event.getDamager() instanceof Player)) return;

        PlayerModel attacker = PlayerModule.getPlayerModel(event.getDamager().getUniqueId());
        PlayerModel defender = PlayerModule.getPlayerModel(event.getEntity().getUniqueId());

        if (attacker.getCurrentTeam() != null && defender.getCurrentTeam() != null) {
            if (attacker.getCurrentTeam() == defender.getCurrentTeam()) {
                event.setCancelled(true);
                attacker.getPlayer().sendMessage(ChatUtils.format("<red>You cannot hurt your fellow teammate!"));
            }
        }
    }
}
