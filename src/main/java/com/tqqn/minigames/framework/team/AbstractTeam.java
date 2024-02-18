package com.tqqn.minigames.framework.team;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing a team in a game.
 * Each team has a name, color, prefix, spawn location, and a list of players.
 */
@Getter
public abstract class AbstractTeam {

    private final List<PlayerModel> teamPlayers = Collections.synchronizedList(new ArrayList<>());
    private final String name;
    private final String teamColor;
    private final String teamPrefix;
    private final Location spawn;

    /**
     * Constructor to initialize a team with specified parameters.
     *
     * @param name        The name of the team.
     * @param teamColor   The color associated with the team.
     * @param teamPrefix  The prefix used to identify the team.
     * @param spawn       The spawn location for the team.
     */
    public AbstractTeam(String name, String teamColor, String teamPrefix, Location spawn) {
        this.name = name;
        this.teamColor = teamColor;
        this.teamPrefix = teamPrefix;
        this.spawn = spawn;
    }

    /**
     * Checks if a player is in the team.
     *
     * @param playerModel The PlayerModel object to check.
     * @return True if the player is in the team, false otherwise.
     */
    public boolean isPlayerInTeam(PlayerModel playerModel) {
        return teamPlayers.contains(playerModel);
    }

    /**
     * Adds a player to the team.
     *
     * @param playerModel The PlayerModel object to add to the team.
     */
    public void addPlayerToTeam(PlayerModel playerModel) {
        if (isPlayerInTeam(playerModel)) return;
        teamPlayers.add(playerModel);
        playerModel.setCurrentTeam(this);
    }

    /**
     * Removes a player from the team.
     *
     * @param playerModel The PlayerModel object to remove from the team.
     */
    public void removePlayerFromTeam(PlayerModel playerModel) {
        if (!isPlayerInTeam(playerModel)) return;
        teamPlayers.remove(playerModel);
        playerModel.setCurrentTeam(null);
    }

    /**
     * Gets the size of the team.
     *
     * @return The number of players in the team.
     */
    public int getTeamSize() {
        return teamPlayers.size()+1;
    }

    /**
     * Abstract method to provide a kit to a player in the team.
     *
     * @param playerModel The PlayerModel object to provide the kit to.
     */
    public abstract void giveKit(PlayerModel playerModel);
}
