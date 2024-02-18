package com.tqqn.minigames.framework.database.models;

import com.tqqn.minigames.framework.scoreboard.PluginScoreboard;
import com.tqqn.minigames.framework.team.AbstractTeam;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class PlayerModel {

    private final UUID uuid; // UUID of the player.
    private final String name; // The name of the player.
    private final PlayerStats stats; // The stats associated with the player.

    @Setter private PluginScoreboard currentScoreboard = null; // Current scoreboard the player has. Default is null.

    @Setter private AbstractTeam currentTeam = null; // Current Team the player is in. Default is null.
    @Setter private boolean spectator; // Flag indicating whether the player is a spectator.
    @Setter private boolean isBat; // Flag indicating whether the player is a bat.

    /**
     * Constructs a new PlayerModel object with the specified UUID, name, and stats.
     *
     * @param uuid  The UUID of the player.
     * @param name  The name of the player.
     * @param stats The stats associated with the player.
     */
    public PlayerModel(UUID uuid, String name, PlayerStats stats) {
        this.uuid = uuid;
        this.name = name;
        this.stats = stats;
        this.spectator = false;
        isBat = false;
    }

    /**
     * Retrieves the Bukkit Player object associated with this player.
     * @return The Bukkit Player object, or null if the player is not online.
     */
    public Player getPlayer() {
        try {
            return Bukkit.getPlayer(uuid);
        } catch (Exception e) {
            return null;
        }
    }
}
