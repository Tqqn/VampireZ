package com.tqqn.minigames.framework.arena;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Arena {

    @Getter private final int minimumPlayers;
    @Getter private final int maximumPlayers;
    private final Location lobbyLocation;
    @Getter private static final Map<UUID, PlayerModel> inGamePlayers = new HashMap<>();

    public Arena(int minimumPlayers, int maximumPlayers, Location lobbyLocation) {
        this.minimumPlayers = minimumPlayers;
        this.maximumPlayers = maximumPlayers;
        this.lobbyLocation = lobbyLocation;
    }

    public void remove() {
        for (PlayerModel playerModel : inGamePlayers.values()) {
            playerModel.spawn(lobbyLocation);
        }
        inGamePlayers.clear();
    }

    public void addPlayerToArena(PlayerModel playerModel) {
        inGamePlayers.put(playerModel.getUuid(), playerModel);
    }

    public void spawnPlayers() {
        for (PlayerModel playerModel : inGamePlayers.values()) {
            playerModel.spawn(playerModel.getCurrentTeam().getSpawn());
        }
    }
}
