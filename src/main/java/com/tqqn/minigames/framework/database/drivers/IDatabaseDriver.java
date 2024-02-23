package com.tqqn.minigames.framework.database.drivers;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;

import java.util.UUID;

public interface IDatabaseDriver {

    void connect(String database, String username, String password, String URI);
    boolean doesPlayerExist(UUID uuid);
    void createPlayerTemplate(UUID uuid, String name);
    PlayerStats getStats(UUID uuid);

    void savePlayer(PlayerModel playerModel);
}
