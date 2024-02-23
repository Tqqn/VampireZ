package com.tqqn.minigames.modules.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.DefaultConfig;
import com.tqqn.minigames.framework.database.config.AbstractConfig;
import com.tqqn.minigames.framework.database.drivers.IDatabaseDriver;
import com.tqqn.minigames.framework.database.listeners.PlayerJoinListener;
import com.tqqn.minigames.framework.database.listeners.PlayerQuitListener;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.drivers.config.ConfigDriver;
import com.tqqn.minigames.modules.database.drivers.mongo.MongoDriver;
import com.tqqn.minigames.modules.player.PlayerModule;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * DatabaseModule class extends AbstractModule and manages database-related functionalities.
 * It handles player data and configurations.
 */
@Getter
public final class DatabaseModule extends AbstractModule {

    private static DefaultConfig defaultConfig;
    private final IDatabaseDriver databaseDriver;

    /**
     * Constructor to initialize DatabaseModule with the specified parameters.
     *
     * @param plugin The VampireZ plugin instance.
     */
    public DatabaseModule(VampireZ plugin) {
        super(plugin, "Database");

        defaultConfig = new DefaultConfig(this);
        databaseDriver = new MongoDriver(this);
    }

    /**
     * Handles module enabling logic.
     */
    @Override
    public void onEnable() {
        setListeners(Arrays.asList(
                new PlayerJoinListener(this),
                new PlayerQuitListener(this)));
        init();
    }

    /**
     * Handles module disabling logic.
     */
    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Bukkit.getLogger().info("Saving player: " + player.getName() + "!");
            savePlayer(PlayerModule.getPlayerModel(player.getUniqueId()));
            player.kick();
            Bukkit.getLogger().info("Finished saving player: " + player.getName() + ". Kicked player from the server.");
        });
        disable();
    }

    public void createPlayerTemplate(UUID uuid, String name) {
        CompletableFuture.runAsync(() -> {
            databaseDriver.createPlayerTemplate(uuid, name);
        });
    }

    /**
     * Saves player data to the database.
     *
     * @param playerModel The PlayerModel object representing the player.
     */
    public void savePlayer(PlayerModel playerModel) {
        CompletableFuture.runAsync(() -> {
            databaseDriver.savePlayer(playerModel);
        });
    }

    /**
     * Loads player data async from the database.
     *
     * @param uuid The UUID of the player.
     * @param name The name of the player.
     * @return CompletableFuture representing the asynchronous loading process.
     */
    public CompletableFuture<PlayerModel> loadPlayer(UUID uuid, String name) {
        return CompletableFuture.supplyAsync(() -> new PlayerModel(uuid, name, databaseDriver.getStats(uuid)));
    }

    /**
     * Checks if a player exists in the database.
     *
     * @param uuid The UUID of the player.
     * @return CompletableFuture representing the asynchronous existence check.
     */
    public CompletableFuture<Boolean> doesPlayerExist(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> databaseDriver.doesPlayerExist(uuid));
    }

    /**
     * Getter for the default configuration.
     *
     * @return The DefaultConfig instance.
     */
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
