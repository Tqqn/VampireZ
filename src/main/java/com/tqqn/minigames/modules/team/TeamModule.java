package com.tqqn.minigames.modules.team;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;
import com.tqqn.minigames.modules.team.teams.Humans;
import com.tqqn.minigames.modules.team.teams.Vampires;

import java.util.LinkedHashMap;

public class TeamModule extends AbstractModule {

    private LinkedHashMap<Class<? extends AbstractTeam>, AbstractTeam> teams;

    public TeamModule(VampireZ plugin) {
        super(plugin, "Team");
    }

    @Override
    public void onEnable() {
        teams = new LinkedHashMap<>();
        teams.put(Humans.class, new Humans());
        teams.put(Vampires.class, new Vampires());
    }

    @Override
    public void onDisable() {
        teams.clear();
    }

    public AbstractTeam whichTeamIsBigger() {
        AbstractTeam abstractTeam = null;
        for (AbstractTeam team : teams.values()) {
            if (abstractTeam == null || team.getTeamSize() > abstractTeam.getTeamSize()) {
                abstractTeam = team;
            }
        }
        return abstractTeam;
    }

    public void addPlayerToTeam(PlayerModel playerModel, Class<? extends AbstractTeam> team) {
        teams.get(team).addPlayerToTeam(playerModel);
    }

    public void removePlayerFromTeam(PlayerModel playerModel, Class<? extends AbstractTeam> team) {
        teams.get(team).addPlayerToTeam(playerModel);
    }
}
