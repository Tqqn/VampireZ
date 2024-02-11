package com.tqqn.minigames.utils;

import lombok.Getter;
import net.kyori.adventure.text.Component;

public enum MessageUtil {

    NO_PERMISSION("<red>You do not have <blue>%s <red>permission to do this."),
    PLAYER_JOIN("%s%s <yellow>has joined the game (<aqua>%s<yellow>/<aqua>%s<yellow>)!"),
    PLAYER_QUIT("%s%s <yellow> has left the game!"),
    VAMPIRE_KILL_HUMAN("%s%s <yellow>was bitten by %s%s <yellow>and turned into a vampire!"),
    SHOUT_CHAT_MESSAGE_FORMAT("<gray>[<aqua>SHOUT<gray>]<reset> %s %s: <white>%s");

    private final String message;
    MessageUtil(String message) {
        this.message = message;
    }

    public Component getMessage() {
        return ChatUtils.format(message);
    }

    public Component getMessage(String... placeholder) {
        return ChatUtils.format(String.format(message, placeholder));
    }
}
