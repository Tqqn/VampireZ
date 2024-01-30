package com.tqqn.minigames.modules.game;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.states.active.ActiveState;
import com.tqqn.minigames.modules.game.states.lobby.LobbyState;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GameModule extends AbstractModule {

    @Getter
    private static GameStates gameState;
    private final Collection<AbstractGameState> loadedGameState;
    public GameModule(VampireZ plugin) {
        super(plugin, Arrays.asList(), "Game");
        gameState = GameStates.LOBBY;
        loadedGameState = new ArrayList<>();
    }

    @Override
    public void onEnable() {
        init();

        LobbyState lobbyState = new LobbyState(this);
        lobbyState.onEnable();
        loadedGameState.add(lobbyState);
    }

    @Override
    public void onDisable() {
        disable();
    }

    public void setGameState(GameStates gameState) {
        if (getGameState() == gameState) return;
        if (getGameState() == GameStates.ACTIVE && gameState == GameStates.LOBBY) return;

        loadedGameState.forEach(state -> {
            state.onDisable();
            loadedGameState.remove(state);
        });

        switch (gameState) {
            case ACTIVE -> {
                GameModule.gameState = GameStates.ACTIVE;
                ActiveState activeState = new ActiveState(this);
                activeState.onEnable();

                loadedGameState.add(activeState);
            }

            case END -> {
                GameModule.gameState = GameStates.END;

            }
        }


    }

}
