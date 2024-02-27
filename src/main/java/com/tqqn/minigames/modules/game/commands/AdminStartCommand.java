package com.tqqn.minigames.modules.game.commands;

import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * AdminStartCommand class implements CommandExecutor and represents the command to forcefully start the game.
 * This command can only be executed by players with the "vampirez.admin" permission.
 */
public final class AdminStartCommand implements CommandExecutor {

    private final GameModule gameModule;

    /**
     * Constructor to initialize AdminStartCommand with the specified parameters.
     *
     * @param gameModule The GameModule instance.
     */
    public AdminStartCommand(GameModule gameModule) {
        this.gameModule = gameModule;
    }

    /**
     * Executes the command to forcefully start the game.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;
        if (!sender.hasPermission("vampirez.admin")) return true;
        gameModule.setGameState(GameStates.ACTIVE);
        sender.sendMessage(ChatUtils.format(ChatUtils.getPrefix() + " <green>You force started the game."));

        PlayerModule.getPlayerModel(sender.getUniqueId()).getStats().increaseStat(PlayerStats.StatType.HUMAN_KILLS); // Debug/test
        return true;
    }
}
