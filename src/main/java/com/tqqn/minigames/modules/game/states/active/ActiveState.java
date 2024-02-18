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

/**
 * ActiveState class extends AbstractGameState and represents the active state of the game.
 * It initializes team options and handles enabling and disabling logic specific to this state.
 */
public final class ActiveState extends AbstractGameState {

    /**
     * Constructor to initialize ActiveState with the specified parameters.
     *
     * @param gameModule The GameModule instance.
     */
    public ActiveState(GameModule gameModule) {
        super(gameModule, "Active");
    }

    /**
     * Handles enabling logic for the active state.
     * It initializes the state, sets up scoreboard teams, and initializes team options.
     */
    @Override
    public void onEnable() {
        init();
        VampireZ.getReflectionLayer().initScoreboardTeams();
        initTeamOptions();
    }

    /**
     * Handles disabling logic for the active state.
     * Currently, there is no specific disabling logic for this state.
     */
    @Override
    public void onDisable() {
        // No specific disabling logic for this state
    }

    /**
     * Initializes team options specific to the active state.
     * It enables flight for vampires and sets name tags for players based on their team affiliation.
     */
    private void initTeamOptions() {
        TeamModule teamModule = (TeamModule) getGameModule().getPlugin().getModuleManager().getModule(TeamModule.class);
        Vampires vampires = (Vampires) teamModule.getTeam(Vampires.class);
        vampires.getTeamPlayers().forEach(vampire -> {
            vampire.getPlayer().setAllowFlight(true);
        });

        Bukkit.getOnlinePlayers().forEach(players -> {
            PlayerModel playerModel = PlayerModule.getPlayerModel(players.getUniqueId());
            if (playerModel.getCurrentTeam().getClass() == Vampires.class) {
                VampireZ.getReflectionLayer().sendNameTag(playerModel.getPlayer(), "100_vampires", "RED", "[V]", "");
            } else if (playerModel.getCurrentTeam().getClass() == Humans.class) {
                VampireZ.getReflectionLayer().sendNameTag(playerModel.getPlayer(), "99_humans", "YELLOW", "[H]", "");
            }
        });
    }
}
