package com.tqqn.minigames.modules.team;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;
import com.tqqn.minigames.framework.team.listeners.DamageByEntityListener;
import com.tqqn.minigames.framework.team.listeners.PlayerDeathListener;
import com.tqqn.minigames.modules.team.commands.ChooseTeamCommand;
import com.tqqn.minigames.modules.team.teams.Humans;
import com.tqqn.minigames.modules.team.teams.Vampires;
import com.tqqn.minigames.utils.ChatUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TeamModule extends AbstractModule {

    private LinkedHashMap<Class<? extends AbstractTeam>, AbstractTeam> teams;

    public TeamModule(VampireZ plugin) {
        super(plugin, "Team");
    }

    @Override
    public void onEnable() {
        setListeners(Arrays.asList(
                new DamageByEntityListener(),
                new PlayerDeathListener(this)));
        setCommands(Map.of("team", new ChooseTeamCommand()));
        init();
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

    public void addPlayerToTeam(PlayerModel playerModel, Class<? extends AbstractTeam> team, boolean sendMessage) {
        if (playerModel.getCurrentTeam() == teams.get(team)) return;

        if (playerModel.getCurrentTeam() != null) {
            teams.get(playerModel.getCurrentTeam().getClass()).removePlayerFromTeam(playerModel);
        }
        teams.get(team).addPlayerToTeam(playerModel);
        if (sendMessage) {
            playerModel.getPlayer().sendMessage(ChatUtils.format("<green>You have chosen the " + teams.get(team).getTeamColor() + teams.get(team).getName() + " <green>team!"));
        }
    }

    public void removePlayerFromTeam(PlayerModel playerModel, Class<? extends AbstractTeam> team) {
        teams.get(team).removePlayerFromTeam(playerModel);
    }
}
