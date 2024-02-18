package com.tqqn.minigames.framework.game.listeners;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.utils.ChatUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class PlayerChatListener implements Listener {


    /**
     * Handles the AsyncChatEvent
     * Cancels the event to prevent default chat behavior.
     * Sends formatted chat messages to players depending if the game is in the lobby state or not.
     */
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.setCancelled(true);
        PlayerModel playerModel = PlayerModule.getPlayerModel(event.getPlayer().getUniqueId());

        if (GameModule.getGameState() == GameStates.LOBBY) {
            Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(ChatUtils.format("<white>" + playerModel.getName() + ": " + ((TextComponent)event.message()).content())));
        } else {
            playerModel.getCurrentTeam().getTeamPlayers().forEach(teamPlayers -> teamPlayers.getPlayer().sendMessage(ChatUtils.format(playerModel.getCurrentTeam().getTeamPrefix() + " " + playerModel.getName() + ": <white>" + ((TextComponent)event.message()).content())));
        }
    }
}
