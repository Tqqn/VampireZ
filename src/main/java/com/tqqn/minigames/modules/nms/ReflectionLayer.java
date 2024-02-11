package com.tqqn.minigames.modules.nms;

import org.bukkit.entity.Player;

public interface ReflectionLayer {

    String getVersionString();
    void sendPacket(Player player, Object packet);

    void disguisePlayerBat(Player player);
}
