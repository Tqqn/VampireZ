package com.tqqn.minigames.framework.database.models;

import com.tqqn.minigames.framework.team.AbstractTeam;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class PlayerModel {

    private final UUID uuid;
    private final String name;
    private final PlayerStats stats;

    @Setter private AbstractTeam currentTeam = null;
    @Setter private boolean spectator;
    @Setter private boolean isBat;

    public PlayerModel(UUID uuid, String name, PlayerStats stats) {
        this.uuid = uuid;
        this.name = name;
        this.stats = stats;
        this.spectator = false;
        isBat = false;
    }

    public Player getPlayer() {
        try {
            return Bukkit.getPlayer(uuid);
        } catch (Exception e) {
            return null;
        }
    }
}
