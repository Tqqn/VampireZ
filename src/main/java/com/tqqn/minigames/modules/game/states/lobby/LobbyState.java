package com.tqqn.minigames.modules.game.states.lobby;

import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.game.states.lobby.listeners.GamePlayerJoinListener;

import java.util.Arrays;

/**
 * LobbyState class extends AbstractGameState and represents the lobby state of the game.
 * It handles enabling and disabling logic specific to this state.
 */
public final class LobbyState extends AbstractGameState {

    /**
     * Constructor to initialize LobbyState with the specified parameters.
     *
     * @param gameModule The GameModule instance.
     */
    public LobbyState(GameModule gameModule) {
        super(gameModule, "Lobby");
    }

    /**
     * Handles enabling logic for the lobby state.
     * It sets up player join listener(s) and initializes the state.
     */
    @Override
    public void onEnable() {
        setListeners(Arrays.asList(new GamePlayerJoinListener()));
        init();
    }

    /**
     * Handles disabling logic for the lobby state.
     * It disables the state.
     */
    @Override
    public void onDisable() {
        disable();
    }
}
