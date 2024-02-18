package com.tqqn.minigames.modules.team.commands;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.utils.ChatUtils;
import com.tqqn.minigames.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * TeamShoutCommand class implements CommandExecutor and represents the command to allowing players to send a message to their other teams.
 * This command can only be executed during the active Game State.
 */
public final class TeamShoutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (GameModule.getGameState() != GameStates.ACTIVE) {
                sender.sendMessage(ChatUtils.format(ChatUtils.getPrefix() + " <red>This command is only enabled during the active game!"));
                return true;
            }

            if (args.length > 0) {
                StringBuilder message = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    if (i > 0) message.append(" ");
                    message.append(args[i]);
                }

                PlayerModel playerModel = PlayerModule.getPlayerModel(sender.getUniqueId());

                Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(MessageUtil.SHOUT_CHAT_MESSAGE_FORMAT.getMessage(playerModel.getCurrentTeam().getTeamPrefix(), playerModel.getName(), message.toString())));
            }
        }
        return true;
    }
}
