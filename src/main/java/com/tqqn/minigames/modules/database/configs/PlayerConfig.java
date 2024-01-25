package com.tqqn.minigames.modules.database.configs;

import com.tqqn.minigames.framework.database.config.AbstractConfig;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.DatabaseModule;

public class PlayerConfig extends AbstractConfig {
    public PlayerConfig(DatabaseModule databaseModule, String configName) {
        super(databaseModule, configName);
    }

    public void savePlayer(PlayerModel playerModel) {
        saveValueToConfig(playerModel.getUuid() + ".name", playerModel.getName());
    }
}
