package com.tqqn.minigames.utils;

import net.kyori.adventure.text.Component;

/**
 * Enum representing various message templates used in the plugin.
 */
public enum MessageUtil {

    NO_PERMISSION("<red>You do not have <blue>%s <red>permission to do this."), // Message indicating lack of permission. - Placeholders = permission-node
    PLAYER_JOIN("%s%s <yellow>has joined the game (<aqua>%s<yellow>/<aqua>%s<yellow>)!"), // Message indicating a player joined a game. - Placeholders = NameColor, PlayerName, current number of players, maximum number of players.
    PLAYER_QUIT("%s%s <yellow> has left the game!"), // Message indicating a player left the game. - Placeholders = NameColor, PlayerName
    VAMPIRE_KILL_HUMAN("%s%s <yellow>was bitten by %s%s <yellow>and turned into a vampire!"), // Message indicating a vampire killed a human. - Placeholders = SurvivorNameColor, SurvivorPlayerName, NameColorVampire, VampirePlayerName
    SHOUT_CHAT_MESSAGE_FORMAT("<gray>[<aqua>SHOUT<gray>]<reset> %s %s: <white>%s"); // Format for shout chat messages. - Placeholders = NameColor, PlayerName, Message

    private final String message;
    MessageUtil(String message) {
        this.message = message;
    }

    /**
     * Gets the formatted message as a Component.
     *
     * @return The formatted message.
     */
    public Component getMessage() {
        return ChatUtils.format(message);
    }

    /**
     * Gets the formatted message with placeholders replaced by the specified strings as a Component.
     *
     * @param placeholder The strings to replace placeholders in the message.
     * @return The formatted message with placeholders replaced.
     */
    public Component getMessage(String... placeholder) {
        return ChatUtils.format(String.format(message, placeholder));
    }
}
