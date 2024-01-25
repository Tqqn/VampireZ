package com.tqqn.minigames.utils;


import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@UtilityClass
public class ChatUtils {

    public static Component fromLegacy(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static Component format(String message) {
        MiniMessage extendedInstances = MiniMessage.builder().build();
        return extendedInstances.deserialize(message);
    }
}
