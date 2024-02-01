package com.tqqn.minigames.framework.team;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractTeam {

    @Getter private final String name;
    private final List<PlayerModel> teamPlayers = Collections.synchronizedList(new ArrayList<>());

    public AbstractTeam(String name) {
        this.name = name;
    }

    public boolean isPlayerInTeam(PlayerModel playerModel) {
        return teamPlayers.contains(playerModel);
    }
}
