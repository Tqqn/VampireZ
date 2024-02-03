package com.tqqn.minigames.framework.team;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractTeam {

    private final List<PlayerModel> teamPlayers = Collections.synchronizedList(new ArrayList<>());

    @Getter private final String name;
    @Getter private final String teamColor;
    @Getter private final String teamPrefix;

    public AbstractTeam(String name, String teamColor, String teamPrefix) {
        this.name = name;
        this.teamColor = teamColor;
        this.teamPrefix = teamPrefix;
    }

    public boolean isPlayerInTeam(PlayerModel playerModel) {
        return teamPlayers.contains(playerModel);
    }

    public void addPlayerToTeam(PlayerModel playerModel) {
        if (isPlayerInTeam(playerModel)) return;
        teamPlayers.add(playerModel);
    }

    public void removePlayerFromTeam(PlayerModel playerModel) {
        if (!isPlayerInTeam(playerModel)) return;
        teamPlayers.remove(playerModel);
    }

    public int getTeamSize() {
        return teamPlayers.size()+1;
    }

    public abstract void giveKit(PlayerModel playerModel);
}
