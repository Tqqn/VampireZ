package com.tqqn.minigames.modules.scoreboard.boards;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.scoreboard.PluginScoreboard;

import java.util.ArrayList;
import java.util.Collection;

public class LobbyBoard extends PluginScoreboard {
    public LobbyBoard() {
        super("Lobby");
    }

    @Override
    public void createScoreboard(PlayerModel playerModel) {
        Collection<String> scoreboard = new ArrayList<>();
        scoreboard.add("");
        scoreboard.add("");
        scoreboard.add("");
    }
}
