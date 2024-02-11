package com.tqqn.minigames.framework.team;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public abstract class AbstractTeam {

    private final List<PlayerModel> teamPlayers = Collections.synchronizedList(new ArrayList<>());
    private final String name;
    private final String teamColor;
    private final String teamPrefix;
    private final Location spawn;

    public AbstractTeam(String name, String teamColor, String teamPrefix, Location spawn) {
        this.name = name;
        this.teamColor = teamColor;
        this.teamPrefix = teamPrefix;
        this.spawn = spawn;
    }

    public boolean isPlayerInTeam(PlayerModel playerModel) {
        return teamPlayers.contains(playerModel);
    }

    public void addPlayerToTeam(PlayerModel playerModel) {
        if (isPlayerInTeam(playerModel)) return;
        teamPlayers.add(playerModel);
        playerModel.setCurrentTeam(this);
    }

    public void removePlayerFromTeam(PlayerModel playerModel) {
        if (!isPlayerInTeam(playerModel)) return;
        teamPlayers.remove(playerModel);
        playerModel.setCurrentTeam(null);
    }

    public int getTeamSize() {
        return teamPlayers.size()+1;
    }

    public abstract void giveKit(PlayerModel playerModel);
}
