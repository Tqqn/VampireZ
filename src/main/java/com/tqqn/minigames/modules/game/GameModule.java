package com.tqqn.minigames.modules.game;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.framework.game.listeners.BlockedMCFeaturesListener;
import com.tqqn.minigames.framework.game.listeners.PlayerChatListener;
import com.tqqn.minigames.framework.game.listeners.PlayerRespawnListener;
import com.tqqn.minigames.framework.team.listeners.PlayerDeathListener;
import com.tqqn.minigames.modules.game.commands.AdminStartCommand;
import com.tqqn.minigames.modules.game.states.active.ActiveState;
import com.tqqn.minigames.modules.game.states.lobby.LobbyState;
import com.tqqn.minigames.modules.team.TeamModule;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * GameModule class extends AbstractModule and manages game-related functionalities.
 * It handles game state transitions, round management, and game events.
 */
public class GameModule extends AbstractModule {

    @Getter private static GameStates gameState;
    @Getter private static GameRounds currentRound;
    private final Collection<AbstractGameState> loadedGameState;

    /**
     * Constructor to initialize GameModule with the specified parameters.
     *
     * @param plugin The VampireZ plugin instance.
     */
    public GameModule(VampireZ plugin) {
        super(plugin, "Game");
        gameState = GameStates.LOBBY;
        loadedGameState = new ArrayList<>();
    }

    /**
     * Handles enabling logic for the game module.
     * It sets up listeners, commands, and initializes the lobby state.
     */
    @Override
    public void onEnable() {
        setListeners(Arrays.asList(new PlayerChatListener(), new BlockedMCFeaturesListener(), new PlayerRespawnListener()));
        setCommands(Map.of("adminstart", new AdminStartCommand(this)));
        init();

        LobbyState lobbyState = new LobbyState(this);
        lobbyState.onEnable();
        loadedGameState.add(lobbyState);
    }

    /**
     * Handles disabling logic for the game module.
     * Currently, there is no specific disabling logic for this module.
     */
    @Override
    public void onDisable() {
        disable();
    }

    /**
     * Sets the game state.
     * It handles state transitions and initializes new game states accordingly.
     *
     * @param gameState The new game state to set.
     */
    public void setGameState(GameStates gameState) {
        if (getGameState() == gameState) return;
        if (getGameState() == GameStates.ACTIVE && gameState == GameStates.LOBBY) return;

        loadedGameState.forEach(AbstractGameState::onDisable);
        loadedGameState.clear();

        switch (gameState) {
            case ACTIVE -> {
                GameModule.gameState = GameStates.ACTIVE;
                ActiveState activeState = new ActiveState(this);
                activeState.onEnable();

                loadedGameState.add(activeState);
                currentRound = GameRounds.ONE;
            }
            case END -> {
                GameModule.gameState = GameStates.END;

            }
        }
    }

    /**
     * Sets the current game round.
     *
     * @param gameRounds The new game round to set.
     */
    public void setCurrentRound(GameRounds gameRounds) {
        GameModule.currentRound = gameRounds;
    }

    /**
     * Spawns lightning strikes randomly around a specified location.
     *
     * @param center       The center location around which lightning strikes will occur.
     * @param howManyTimes The number of lightning strikes to spawn.
     */
    public void spawnLightningRandom(Location center, int howManyTimes) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        center.add((random.nextBoolean() ? 1: -1) * random.nextInt(10),
                (random.nextBoolean() ? 1: -1) * random.nextInt(10),
                (random.nextBoolean() ? 1: -1) * random.nextInt(10));
        center.setY(center.getWorld().getHighestBlockYAt(center));

        for (int i = 1; i < howManyTimes; i++) {
            center.getWorld().strikeLightning(center);
        }
    }

    /**
     * Enumeration representing different game rounds.
     */
    @Getter
    public enum GameRounds {
        ONE("I"),
        TWO("II"),
        THREE("III"),
        FOUR("IV"),
        FIVE("V"),
        SIX("VI"),
        SEVEN("VII"),
        EIGHT("VIII"),
        NINE("IX"),
        TEN("X"),
        FINAL("Final");

        private final String prettyName;
        GameRounds(String prettyName) {
            this.prettyName = prettyName;
        }

        /**
         * Retrieves the next game round.
         *
         * @return The next game round.
         */
        public GameRounds getNext() {
            if (ordinal() == values().length -1) return null;
            return values()[ordinal() +1];
        }
    }
}
