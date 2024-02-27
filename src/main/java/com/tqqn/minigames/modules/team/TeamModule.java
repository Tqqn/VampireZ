package com.tqqn.minigames.modules.team;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;
import com.tqqn.minigames.framework.team.listeners.DamageByEntityListener;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.team.abilities.VampireFlyAbility;
import com.tqqn.minigames.modules.team.commands.ChooseTeamCommand;
import com.tqqn.minigames.modules.team.commands.TeamShoutCommand;
import com.tqqn.minigames.modules.team.teams.Humans;
import com.tqqn.minigames.modules.team.teams.Vampires;
import com.tqqn.minigames.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages teams in the game.
 */
public final class TeamModule extends AbstractModule {

    private LinkedHashMap<Class<? extends AbstractTeam>, AbstractTeam> teams;

    /**
     * Constructs a new TeamModule instance.
     *
     * @param plugin The main plugin instance.
     */
    public TeamModule(VampireZ plugin) {
        super(plugin, "Team");
    }

    /**
     * Called when the module is enabled.
     * Registers listeners, commands, initializes teams, and abilities.
     */
    @Override
    public void onEnable() {
        setListeners(Arrays.asList(
                new DamageByEntityListener()));
        setCommands(Map.of("team", new ChooseTeamCommand(), "shout", new TeamShoutCommand()));
        init();
        registerAbilities();
        teams = new LinkedHashMap<>();
        Bukkit.getScheduler().runTask(getPlugin(), this::registerTeams);
    }

    /**
     * Called when the module is disabled.
     * Clears the collection of teams.
     */
    @Override
    public void onDisable() {
        teams.clear();
    }

    /**
     * Registers the teams for the game.
     */
    public void registerTeams() {
        teams.put(Humans.class, new Humans((DatabaseModule) getPlugin().getModuleManager().getModule(DatabaseModule.class)));
        teams.put(Vampires.class, new Vampires((DatabaseModule) getPlugin().getModuleManager().getModule(DatabaseModule.class)));
    }

    /**
     * Registers abilities related to teams.
     */
    public void registerAbilities() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new VampireFlyAbility(), getPlugin());
    }

    /**
     * Retrieves a team by its class.
     *
     * @param team The class of the team.
     * @return The team instance, or null if not found.
     */
    public AbstractTeam getTeam(Class<? extends AbstractTeam> team) {
        return teams.get(team);
    }

    /**
     * Determines which team has more players.
     * @return The team with the most players.
     */
    public AbstractTeam whichTeamIsBigger() {
        AbstractTeam abstractTeam = null;
        for (AbstractTeam team : teams.values()) {
            if (abstractTeam == null || team.getTeamSize() > abstractTeam.getTeamSize()) {
                abstractTeam = team;
            }
        }
        return abstractTeam;
    }

    /**
     * Adds a player to a team.
     *
     * @param playerModel The PlayerModel of the player.
     * @param team The class of the team to join.
     * @param sendMessage True to send a message to the player indicating their team choice.
     */
    public void addPlayerToTeam(PlayerModel playerModel, Class<? extends AbstractTeam> team, boolean sendMessage) {
        if (playerModel.getCurrentTeam() == teams.get(team)) return;

        if (playerModel.getCurrentTeam() != null) {
            teams.get(playerModel.getCurrentTeam().getClass()).removePlayerFromTeam(playerModel);
        }
        teams.get(team).addPlayerToTeam(playerModel);
        if (sendMessage) {
            playerModel.getPlayer().sendMessage(ChatUtils.format(ChatUtils.getPrefix() + " <green>You have chosen the " + teams.get(team).getTeamColor() + teams.get(team).getName() + " <green>team!"));
        }
    }
}
