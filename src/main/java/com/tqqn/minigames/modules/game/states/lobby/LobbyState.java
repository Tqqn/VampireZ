package com.tqqn.minigames.modules.game.states.lobby;

import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.modules.game.GameModule;

import java.util.Arrays;

public class LobbyState extends AbstractGameState {
    public LobbyState(GameModule gameModule) {
        super(gameModule, Arrays.asList(), "Lobby");
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        disable();
    }
}
