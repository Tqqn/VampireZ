package com.tqqn.minigames.modules.game.states.active;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.modules.team.TeamModule;
import com.tqqn.minigames.modules.team.teams.Humans;
import com.tqqn.minigames.modules.team.teams.Vampires;
import org.bukkit.Bukkit;

public final class ActiveState extends AbstractGameState {
    public ActiveState(GameModule gameModule) {
        super(gameModule, "Active");
    }

    @Override
    public void onEnable() {
        init();
        VampireZ.getNMS().initScoreboardTeams();
        initTeamOptions();
    }

    @Override
    public void onDisable() {

    }

    private void initTeamOptions() {
        TeamModule teamModule = (TeamModule) getGameModule().getPlugin().getModuleManager().getModule(TeamModule.class);
        Vampires vampires = (Vampires) teamModule.getTeam(Vampires.class);
        vampires.getTeamPlayers().forEach(vampire -> {
            vampire.getPlayer().setAllowFlight(true);
        });

        Bukkit.getOnlinePlayers().forEach(players -> {
            PlayerModel playerModel = PlayerModule.getPlayerModel(players.getUniqueId());
            if (playerModel.getCurrentTeam().getClass() == Vampires.class) {
                VampireZ.getNMS().sendNameTag(playerModel.getPlayer(), "100_vampires", "RED", "[V]", "");
            } else if (playerModel.getCurrentTeam().getClass() == Humans.class) {
                VampireZ.getNMS().sendNameTag(playerModel.getPlayer(), "99_humans", "YELLOW", "[H]", "");
            }
        });
    }
}
