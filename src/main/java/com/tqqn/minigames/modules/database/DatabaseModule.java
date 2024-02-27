package com.tqqn.minigames.modules.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.DefaultConfig;
import com.tqqn.minigames.framework.database.drivers.IDatabaseDriver;
import com.tqqn.minigames.framework.database.listeners.PlayerLoadListeners;
import com.tqqn.minigames.framework.database.listeners.PlayerQuitListener;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.drivers.MongoDriver;
import com.tqqn.minigames.modules.player.PlayerModule;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Arrays;
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
    private PlayerModule playerModule;

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
                new PlayerLoadListeners(this),
                new PlayerQuitListener(this)));
        databaseDriver.connect("players", "dev-toon-mongodb-1", "27017");
        playerModule = (PlayerModule) getPlugin().getModuleManager().getModule(PlayerModule.class);
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
        CompletableFuture.runAsync(() -> databaseDriver.createPlayerTemplate(uuid, name));
    }

    /**
     * Saves player data to the database.
     *
     * @param playerModel The PlayerModel object representing the player.
     */
    public void savePlayer(PlayerModel playerModel) {
        CompletableFuture.runAsync(() -> databaseDriver.savePlayer(playerModel));
    }

    /**
     * Reads player data sync from the database
     * @param uuid The UUID of the player.
     * @param name The name of the player.
     * @return PlayerModel
     */
    public PlayerModel getPlayer(UUID uuid, String name) {
        if (databaseDriver.getStats(uuid) == null) return null;
        return new PlayerModel(uuid, name, databaseDriver.getStats(uuid));
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
