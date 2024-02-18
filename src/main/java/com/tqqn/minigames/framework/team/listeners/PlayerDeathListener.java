package com.tqqn.minigames.framework.team.listeners;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.modules.team.TeamModule;
import com.tqqn.minigames.modules.team.teams.Humans;
import com.tqqn.minigames.modules.team.teams.Vampires;
import com.tqqn.minigames.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.world.WorldLoadEvent;

public final class PlayerDeathListener implements Listener {

    private final TeamModule teamModule;

    public PlayerDeathListener(TeamModule teamModule) {
        this.teamModule = teamModule;
    }


    /**
     * Handles the PlayerDeathEvent
     * It customizes the death message, clears dropped experience points and items, and handles player respawn.
     * Additionally, it adjusts the player's team affiliation based on the game logic.
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");
        event.setDroppedExp(0);
        event.getDrops().clear();

        PlayerModel killedPlayer = PlayerModule.getPlayerModel(event.getPlayer().getUniqueId());
        Component message;

        if (event.getPlayer().getKiller() != null) {
            PlayerModel killer = PlayerModule.getPlayerModel(event.getPlayer().getKiller().getUniqueId());
            message = MessageUtil.VAMPIRE_KILL_HUMAN.getMessage("<red>", killedPlayer.getName(), "<red>", killer.getName());
        } else {
            message = null;
        }

        if (killedPlayer.getCurrentTeam().getClass() == Humans.class) {
            if (message != null) {
                Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(message));
            }
            teamModule.addPlayerToTeam(killedPlayer, Vampires.class, false);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(teamModule.getPlugin(), () -> event.getPlayer().spigot().respawn(),2L);
    }
}
