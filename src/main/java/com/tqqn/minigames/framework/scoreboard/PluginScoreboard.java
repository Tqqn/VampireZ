package com.tqqn.minigames.framework.scoreboard;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents an abstract plugin scoreboard.
 */
public abstract class PluginScoreboard {

    @Getter private final String name;
    private final ConcurrentHashMap<UUID, PlayerModel> scoreboardPlayers;

    /**
     * Constructs a new PluginScoreboard object with the specified name.
     *
     * @param name The name of the scoreboard.
     */
    public PluginScoreboard(String name) {
        this.name = name;
        this.scoreboardPlayers = new ConcurrentHashMap<>();
    }

    /**
     * Creates a scoreboard for the specified player model.
     *
     * @param playerModel The PlayerModel for which to create the scoreboard.
     */
    public abstract void createScoreboard(PlayerModel playerModel);

    /**
     * Sets the scoreboard for the specified player model.
     * If the player model already has a scoreboard, it will be replaced.
     *
     * @param playerModel The PlayerModel for which to set the scoreboard.
     */
    public void setScoreboard(PlayerModel playerModel) {
        if (scoreboardPlayers.contains(playerModel)) removeScoreboard(playerModel);
        scoreboardPlayers.put(playerModel.getUuid(), playerModel);
    }

    /**
     * Removes the scoreboard for the specified player model.
     *
     * @param playerModel The PlayerModel for which to remove the scoreboard.
     */
    public void removeScoreboard(PlayerModel playerModel) {
        scoreboardPlayers.remove(playerModel.getUuid());
    }
}
