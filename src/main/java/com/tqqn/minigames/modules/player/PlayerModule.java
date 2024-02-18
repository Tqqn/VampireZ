package com.tqqn.minigames.modules.player;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.database.configs.PlayerConfig;
import com.tqqn.minigames.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
     * Processes the first login of a player.
     * This method creates a player template in the player configuration file if it doesn't exist already.
     *
     * @param player The player object representing the player who is logging in for the first time.
     */
    public void processFirstLogin(Player player) {
        PlayerConfig playerConfig = (PlayerConfig) databaseModule.getLoadedCustomConfigs().get("players.yml");
        playerConfig.createPlayerTemplate(player.getUniqueId(), player.getName());
    }

    /**
     * Processes the login of a player.
     *
     * @param player The player object representing the player who is logging in.
     */
    public void processLogin(Player player) {
        try {
            if (!databaseModule.doesPlayerExist(player.getUniqueId()).get()) {
                processFirstLogin(player);
            }
        } catch (ExecutionException | InterruptedException e) {
            Bukkit.getLogger().info("Something went wrong getting the playerdata of " + player.getName());
            player.kickPlayer("Something went wrong getting your playerdata! Try it again later.");
        }

        PlayerModel playerModel;

        try {
            playerModel = databaseModule.loadPlayer(player.getUniqueId(), player.getName()).get();
        } catch (Exception ignored) {
            return;
        }

        CACHED_PLAYERS.put(player.getUniqueId(), playerModel);

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
