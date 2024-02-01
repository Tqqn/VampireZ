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

    public PlayerStats getStats(UUID uuid) {
        return new PlayerStats(
                getCustomConfig().getInt(uuid + ".stats.human_kills"),
                getCustomConfig().getInt(uuid + ".stats.human_wins"),
                getCustomConfig().getInt(uuid + ".stats.human_losses"),
                getCustomConfig().getInt(uuid + ".stats.human_deaths"),
                getCustomConfig().getInt(uuid + ".stats.vampire_kills"),
                getCustomConfig().getInt(uuid + ".stats.vampire_wins"),
                getCustomConfig().getInt(uuid + ".stats.vampire_losses"),
                getCustomConfig().getInt(uuid + ".stats.vampire_deaths"));
    }

    public void savePlayer(PlayerModel playerModel) {
        saveValueToConfig(playerModel.getUuid() + ".name", playerModel.getName());
    }
}
