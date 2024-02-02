package com.tqqn.minigames.modules.database.configs;

import com.tqqn.minigames.framework.database.config.AbstractConfig;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;

import java.util.UUID;

public class PlayerConfig extends AbstractConfig {
    public PlayerConfig(DatabaseModule databaseModule, String configName) {
        super(databaseModule, configName);
    }

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

    public void savePlayer(PlayerModel playerModel) {
        saveValueToConfig(playerModel.getUuid() + ".name", playerModel.getName());
        saveCustomConfig();
    }
}
