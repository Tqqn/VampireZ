package com.tqqn.minigames.modules.player;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.events.GamePlayerJoinEvent;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.utils.ChatUtils;
import com.tqqn.minigames.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ExecutionException;

public final class PlayerModule extends AbstractModule {

    private static final HashMap<UUID, PlayerModel> CACHED_PLAYERS = new HashMap<>(); // Cached Player Map
    private final DatabaseModule databaseModule; // DatabaseModule variable

    /**
     * Constructs a new PlayerModule object.
     *
     * @param plugin The VampireZ plugin instance.
     */
    public PlayerModule(VampireZ plugin) {
        super(plugin, "Player");
        this.databaseModule = (DatabaseModule) getPlugin().getModuleManager().getModule(DatabaseModule.class);
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        disable();
    }

    /**
     * Retrieves the PlayerModel associated with the specified UUID from the cache.
     *
     * @param uuid The UUID of the player.
     * @return The PlayerModel associated with the specified UUID, or null if none exists.
     */
    public static PlayerModel getPlayerModel(UUID uuid) {
        return CACHED_PLAYERS.get(uuid);
    }

    /**
     * Adds a player model to the cached players map.
     *
     * @param playerModel The player model to add to the cache.
     */
    public void cachePlayerModel(PlayerModel playerModel) {
        CACHED_PLAYERS.put(playerModel.getUuid(), playerModel);
    }

    /**
     * Processes the login with spigot methods - this is called after the player is loaded from the DB.
     *
     * @param player The player object representing the player who is logging in.
     */
    public void processSpigotLogin(Player player) {

        player.getInventory().clear();

        player.teleport(databaseModule.getDefaultConfig().getSpawn("lobby"));
        player.setInvisible(false);

        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(MessageUtil.PLAYER_JOIN.getMessage("<red>", player.getName(), "1", "1")));
    }

    /**
     * Sets the specified PlayerModel as a spectator.
     *
     * @param playerModel The PlayerModel to set as a spectator.
     */
    public void setSpectator(PlayerModel playerModel) {
        playerModel.setSpectator(true);
        Player player = Bukkit.getPlayer(playerModel.getUuid());
        if (!player.isOnline()) return;
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2000000, 1));
        Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(player));
    }
}
