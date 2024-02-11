package com.tqqn.minigames.modules.team.abilities;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.framework.team.abilities.IAbility;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.modules.team.teams.Vampires;
import com.tqqn.minigames.utils.ChatUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class VampireFlyAbility implements IAbility, Listener {

    @Override
    public void perform() {
    }

    @Override
    public void stop() {
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (GameModule.getGameState() != GameStates.ACTIVE) return;
        if (!PlayerModule.getPlayerModel(event.getPlayer().getUniqueId()).isBat()) return;
        VampireZ.getNMS().removeBat(event.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (GameModule.getGameState() != GameStates.ACTIVE) return;
        if (!(event.getEntity() instanceof Player) && !(event.getEntity() instanceof Player)) return;
        PlayerModel attacker = PlayerModule.getPlayerModel(event.getDamager().getUniqueId());
        if (!attacker.getPlayer().isFlying()) return;
        PlayerModel defender = PlayerModule.getPlayerModel(event.getEntity().getUniqueId());
        if (defender.getCurrentTeam().getClass() == Vampires.class) return;
        event.setCancelled(true);
        attacker.getPlayer().sendMessage(ChatUtils.format("<red>You cannot hurt survivors while you are in bat form."));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!PlayerModule.getPlayerModel(event.getPlayer().getUniqueId()).isBat()) return;
        VampireZ.getNMS().moveBat(event.getPlayer());
    }

    @EventHandler
    public void onToggleFly(PlayerToggleFlightEvent event) {
        PlayerModel playerModel = PlayerModule.getPlayerModel(event.getPlayer().getUniqueId());
        if (playerModel.getCurrentTeam().getClass() != Vampires.class) return;
        if (event.isFlying()) {
            event.getPlayer().sendMessage(ChatUtils.format("<red>You tranformed into a bat. You can fly now."));
            VampireZ.getNMS().disguisePlayerBat(event.getPlayer());
        } else {
            event.getPlayer().sendMessage(ChatUtils.format("<red>You transformed back to a vampire."));
            VampireZ.getNMS().removeBat(event.getPlayer());
        }

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(144, 23, 23), 1.0F);

        for (int i = 0; i <= 10; i++) {
            Location location = event.getPlayer().getLocation();
            location.setX(location.getX() + Math.cos(i));
            location.setZ(location.getZ() + Math.sin(i));
            location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, dustOptions);
            location.getWorld().spawnParticle(Particle.SMOKE_LARGE, location, 1);
            //location.getWorld().spawnParticle(Particle.SPELL_WITCH, location, 1);

        }

    }
}
