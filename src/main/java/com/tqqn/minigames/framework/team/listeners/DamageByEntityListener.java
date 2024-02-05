package com.tqqn.minigames.framework.team.listeners;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.player.PlayerModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) && !(event.getDamager() instanceof Player)) {
            PlayerModel attacker = PlayerModule.getPlayerModel(event.getDamager().getUniqueId());
            PlayerModel defender = PlayerModule.getPlayerModel(event.getEntity().getUniqueId());

            if (attacker.getCurrentTeam() != null && defender.getCurrentTeam() != null) {
                if (attacker.getCurrentTeam() == defender.getCurrentTeam()) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
