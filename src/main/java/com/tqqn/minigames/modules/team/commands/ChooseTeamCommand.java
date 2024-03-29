package com.tqqn.minigames.modules.team.commands;

import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.menu.MenuModule;
import com.tqqn.minigames.modules.team.menus.SelectTeamMenu;
import com.tqqn.minigames.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * ChooseTeamCommand class implements CommandExecutor and represents the command to open a team selector for players.
 * This is only possible during the Lobby state.
 */
public final class ChooseTeamCommand implements CommandExecutor {


    /**
     * Executes the command to open a Team-Selector GUI.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (GameModule.getGameState() != GameStates.LOBBY) {
                sender.sendMessage(ChatUtils.format(ChatUtils.getPrefix() + " <red>This command is disabled during the game!"));
                return true;
            }
            MenuModule.getMenu(SelectTeamMenu.class).open(sender);
        }
        return true;
    }
}
