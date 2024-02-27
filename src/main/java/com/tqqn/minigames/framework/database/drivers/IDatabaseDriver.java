package com.tqqn.minigames.framework.database.drivers;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;

import java.util.UUID;

public interface IDatabaseDriver {

    /**
     * Connects to the database server.
     *
     * @param database The name of the database to connect to.
     * @param host     The host address of the database server.
     * @param port     The port number of the database server.
     */
    void connect(String database, String host, String port);

    /**
     * Creates a player template in the database with the given UUID and name.
     *
     * @param uuid The UUID of the player.
     * @param name The name of the player.
     */
    void createPlayerTemplate(UUID uuid, String name);

    /**
     * Retrieves player statistics from the database based on the given UUID.
     *
     * @param uuid The UUID of the player.
     * @return The PlayerStats object containing the player's statistics.
     */
    PlayerStats getStats(UUID uuid);

    /**
     * Saves the player model by updating the player data in the database.
     *
     * @param playerModel The PlayerModel object to save.
     */
    void savePlayer(PlayerModel playerModel);
}
