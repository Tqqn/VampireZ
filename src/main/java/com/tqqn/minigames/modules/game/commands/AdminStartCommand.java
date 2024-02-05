package com.tqqn.minigames.modules.game.commands;

import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.GameModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminStartCommand implements CommandExecutor {

    private final GameModule gameModule;

    public AdminStartCommand(GameModule gameModule) {
        this.gameModule = gameModule;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;
        if (!sender.hasPermission("vampirez.admin")) return true;
        gameModule.setGameState(GameStates.ACTIVE);
        return true;
    }
}
