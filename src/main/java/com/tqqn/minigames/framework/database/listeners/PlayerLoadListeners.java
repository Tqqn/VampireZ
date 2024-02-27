package com.tqqn.minigames.framework.database.listeners;

import com.tqqn.minigames.framework.database.events.GamePlayerJoinEvent;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerLoadListeners implements Listener {

    private final DatabaseModule databaseModule;
    private final PlayerModule playerModule;
    private final ConcurrentHashMap<UUID, PlayerModel> joiningPlayers = new ConcurrentHashMap<>();

    public PlayerLoadListeners(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
        this.playerModule = (PlayerModule) databaseModule.getPlugin().getModuleManager().getModule(PlayerModule.class);
    }

    /**
     * Handles the AsyncPlayerPreLoginEvent (Called async!!)
     * This method retrieves the player model from the database based on the player's unique ID and name.
     * If the player model is not found, it creates a new player template in the database and initializes a new player model.
     * Finally, it adds the player model to the joining players map for further processing.
     *
     * @param event The AsyncPlayerPreLoginEvent triggered before a player logs in to the server.
     */
    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {

        PlayerModel playerModel = databaseModule.getPlayer(event.getUniqueId(), event.getName());

        if (playerModel == null) {
            databaseModule.createPlayerTemplate(event.getUniqueId(), event.getName());
            playerModel = new PlayerModel(event.getUniqueId(), event.getName());
        }
        joiningPlayers.put(event.getUniqueId(), playerModel);
    }

    /**
     * Handles the PlayerLoginEvent
     * This method retrieves the player model from the joining players map and kicks the player from the server
     * with an error message if the player model is not found.
     *
     * @param event The PlayerLoginEvent triggered when a player logs in to the server.
     */
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        PlayerModel playerModel = joiningPlayers.get(event.getPlayer().getUniqueId());
        if (playerModel == null) {
            event.kickMessage(ChatUtils.format("<red>Something went wrong getting your playerdata! Try it again later."));
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    /**
     * Handles the PlayerJoinEvent
     * This method clears the default join message, retrieves the player model from the joining players map by removing it,
     * caches the player model, updates the player's name in the model if it has changed,
     * triggers a custom event for player join, and processes the player's login on the Spigot server.
     *
     * @param event The PlayerJoinEvent triggered when a player joins the server.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        final Player player = event.getPlayer();

        PlayerModel playerModel = joiningPlayers.remove(player.getUniqueId());

        playerModule.cachePlayerModel(playerModel);

        if (!playerModel.getName().equals(player.getName())) playerModel.setName(player.getName());

        Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(PlayerModule.getPlayerModel(player.getUniqueId())));

        playerModule.processSpigotLogin(player);
    }
}
