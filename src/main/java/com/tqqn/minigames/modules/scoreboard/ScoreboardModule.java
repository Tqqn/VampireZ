package com.tqqn.minigames.modules.scoreboard;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.scoreboard.PluginScoreboard;

import java.util.HashMap;

/**
 * The ScoreboardModule class represents a module responsible for managing different types of scoreboards
 * in the VampireZ plugin.
 */
public class ScoreboardModule extends AbstractModule {

    private final HashMap<Class<? extends PluginScoreboard>, PluginScoreboard> loadedScoreboards;

    /**
     * Constructs a new instance of the ScoreboardModule class with the provided plugin.
     * Initializes the map for loaded scoreboards.
     *
     * @param plugin The plugin instance for which this class is constructed.
     */
    public ScoreboardModule(VampireZ plugin) {
        super(plugin, "Scoreboard");
        loadedScoreboards = new HashMap<>();
    }

    /**
     * Called when the module is enabled.
     * This method is executed when the module is enabled, but currently, it does not perform any specific action.
     */
    @Override
    public void onEnable() {

    }

    /**
     * Called when the module is disabled.
     * This method is executed when the module is disabled, but currently, it does not perform any specific action.
     */
    @Override
    public void onDisable() {

    }

}
