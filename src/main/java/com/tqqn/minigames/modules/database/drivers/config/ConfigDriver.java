package com.tqqn.minigames.modules.database.drivers.config;

import com.tqqn.minigames.framework.database.config.AbstractConfig;
import com.tqqn.minigames.framework.database.drivers.IDatabaseDriver;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;

import java.util.UUID;

/**
 * PlayerConfig class extends AbstractConfig and provides methods for managing player configurations.
 * It allows creating player templates, retrieving player statistics, and saving player data.
 */
public final class ConfigDriver implements IDatabaseDriver {

    /**
     * Constructor to initialize PlayerConfig with the specified parameters.
     *
     * @param databaseModule The DatabaseModule instance.
     * @param configName     The name of the configuration.
     */
    public ConfigDriver(DatabaseModule databaseModule, String configName) {
        super(databaseModule, configName);
    }


    /**
     * Creates a player template with default statistics and saves it to the configuration.
     *
     * @param uuid The UUID of the player.
     * @param name The name of the player.
     */
    @Override
    public void createPlayerTemplate(UUID uuid, String name) {
        saveValueToConfig(uuid + ".name", name);
        saveValueToConfig(uuid + PlayerStats.StatType.HUMAN_KILLS.getConfigPath(), 0);
        saveValueToConfig(uuid + PlayerStats.StatType.HUMAN_WINS.getConfigPath(), 0);
        saveValueToConfig(uuid + PlayerStats.StatType.HUMAN_LOSSES.getConfigPath(), 0);
        saveValueToConfig(uuid + PlayerStats.StatType.HUMAN_DEATHS.getConfigPath(), 0);
        saveValueToConfig(uuid + PlayerStats.StatType.VAMPIRE_KILLS.getConfigPath(), 0);
        saveValueToConfig(uuid + PlayerStats.StatType.VAMPIRE_WINS.getConfigPath(), 0);
        saveValueToConfig(uuid + PlayerStats.StatType.VAMPIRE_LOSSES.getConfigPath(), 0);
        saveValueToConfig(uuid + PlayerStats.StatType.VAMPIRE_DEATHS.getConfigPath(), 0);
        saveCustomConfig();
    }

    /**
     * Retrieves the player statistics from the configuration.
     *
     * @param uuid The UUID of the player.
     * @return PlayerStats object containing the player's statistics.
     */
    @Override
    public PlayerStats getStats(UUID uuid) {
        return new PlayerStats(
                getCustomConfig().getInt(uuid + PlayerStats.StatType.HUMAN_KILLS.getConfigPath()),
                getCustomConfig().getInt(uuid + PlayerStats.StatType.HUMAN_WINS.getConfigPath()),
                getCustomConfig().getInt(uuid + PlayerStats.StatType.HUMAN_LOSSES.getConfigPath()),
                getCustomConfig().getInt(uuid + PlayerStats.StatType.HUMAN_DEATHS.getConfigPath()),
                getCustomConfig().getInt(uuid + PlayerStats.StatType.VAMPIRE_KILLS.getConfigPath()),
                getCustomConfig().getInt(uuid + PlayerStats.StatType.VAMPIRE_WINS.getConfigPath()),
                getCustomConfig().getInt(uuid + PlayerStats.StatType.VAMPIRE_LOSSES.getConfigPath()),
                getCustomConfig().getInt(uuid + PlayerStats.StatType.VAMPIRE_DEATHS.getConfigPath()));
    }

    /**
     * Saves the player data to the configuration.
     *
     * @param playerModel The PlayerModel object representing the player.
     */
    @Override
    public void savePlayer(PlayerModel playerModel) {
        saveValueToConfig(playerModel.getUuid() + ".name", playerModel.getName());
        saveCustomConfig();
    }
}
