package com.tqqn.minigames.framework.team.listeners;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.modules.team.TeamModule;
import com.tqqn.minigames.modules.team.teams.Humans;
import com.tqqn.minigames.modules.team.teams.Vampires;
import com.tqqn.minigames.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final TeamModule teamModule;

    public PlayerDeathListener(TeamModule teamModule) {
        this.teamModule = teamModule;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");

        PlayerModel killedPlayer = PlayerModule.getPlayerModel(event.getPlayer().getUniqueId());
        String message;

        if (event.getPlayer().getKiller() != null) {
            PlayerModel killer = PlayerModule.getPlayerModel(event.getPlayer().getKiller().getUniqueId());
            message = String.valueOf(MessageUtil.VAMPIRE_KILL_HUMAN.getMessage("<red>", killedPlayer.getName(), "<red>", killer.getName()));
        } else {
            message = null;
        }

        if (killedPlayer.getCurrentTeam().getClass() == Humans.class) {
            if (message != null) {
                Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(message));
            }
            teamModule.addPlayerToTeam(killedPlayer, Vampires.class, false);
        }
    }
}
