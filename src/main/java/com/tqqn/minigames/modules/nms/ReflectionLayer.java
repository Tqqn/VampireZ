package com.tqqn.minigames.modules.nms;

import org.bukkit.entity.Player;

/**
 * The ReflectionLayer interface defines methods for interacting with Minecraft's server reflection.
 * It provides methods for retrieving the server version string, sending packets to players,
 * and performing specific actions such as disguising players as bats.
 */
public interface ReflectionLayer {

    /**
     * Retrieves the version string of the Minecraft server.
     * This method returns a string representing the version of the Minecraft server,
     * such as "1.17.1" or "1.18".
     * @return The version string of the Minecraft server.
     */
    String getVersionString();

    /**
     * Sends a packet to the specified player using server reflection.
     * This method sends a packet object to the given player using server reflection,
     * allowing for the manipulation of game entities and events at a low level.
     *
     * @param player      The player to whom the packet is being sent.
     * @param packet      The packet object to be sent to the player.
     */
    void sendPacket(Player player, Object packet);

    /**
     * Disguises the specified player as a bat entity.
     * This method disguises the player as a bat entity by modifying game data and sending packets
     * to all players to reflect the disguise. The player will appear as a bat to other players
     * until the disguise is removed.
     *
     * @param player The player to disguise as a bat.
     */
    void disguisePlayerBat(Player player);

    /**
     * Moves the disguised bat entity to the specified player's current location.
     * This method updates the bat entity's position and head rotation,
     * then sends packets to all online players to teleport the bat entity and update its rotation.
     *
     * @param player The player whose bat entity is being moved.
     */
    void moveBat(Player player);

    /**
     * Removes the disguised bat entity from the game.
     * This method sends packets to all online players to remove the bat entity,
     * updates the player's bat status, and makes the player visible again.
     *
     * @param player The player whose bat entity is being removed.
     */
    void removeBat(Player player);

    /**
     * Sends a custom name tag to the specified player.
     * This method creates a custom team with the specified name, color, prefix, and suffix,
     * adds the player to the team, and sends packets to all online players to display the custom name tag for the player.
     *
     * @param player   The player to whom the custom name tag is being sent.
     * @param teamName The name of the custom team.
     * @param color    The color of the name tag.
     * @param prefix   The prefix to be displayed before the player's name.
     * @param suffix   The suffix to be displayed after the player's name.
     */
    void sendNameTag(Player player, String teamName, String color, String prefix, String suffix);

    /**
     * Initializes the scoreboard teams for vampires and humans.
     * If the teams do not exist, this method creates them and sets collision rules to NEVER
     * to prevent player collision within the teams.
     */
    void initScoreboardTeams();

    /**
     * DEBUG/TEST METHOD
     * Sends a sidebar scoreboard to the specified player.
     * This method creates a new scoreboard with a dummy objective, adds the player to a new team for the sidebar,
     * sets the display name of the objective, and sends packets to the player to display the sidebar scoreboard.
     *
     * @param player The player to whom the sidebar scoreboard is being sent.
     */
    void sendSideBarScoreboard(Player player);
}
