package com.tqqn.minigames.modules.game.states.active.runnables;

import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.framework.game.events.SwitchGameRoundEvent;
import com.tqqn.minigames.modules.game.GameModule;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * ActiveRunnable class extends BukkitRunnable and represents the task for managing the active state of the game.
 * It handles countdowns for the game round and overall game time.
 */
public class ActiveRunnable extends BukkitRunnable {

    private final AbstractGameState state;
    private static int time = 560; // The total time remaining for the game.
    private static int roundTime = 60; // The time remaining for the current game round.

    /**
     * Constructor to initialize ActiveRunnable with the specified parameters.
     *
     * @param state The AbstractGameState instance.
     */
    public ActiveRunnable(AbstractGameState state) {
        this.state = state;
    }

    /**
     * Executes the task.
     * It decrements roundTime and time and handles events based on their values.
     */
    @Override
    public void run() {
        // Check if the round time has reached 0
        if (roundTime == 0) {
            // Trigger event to switch to the next game round
            GameModule.GameRounds nextRound = GameModule.getCurrentRound().getNext();
            Bukkit.getPluginManager().callEvent(new SwitchGameRoundEvent(GameModule.getGameState(), GameModule.getCurrentRound(), nextRound));
            roundTime = 60;
        }
        // Check if the total game time has reached 0
        if (time == 0) {
            // End the game
            cancel();
            state.getGameModule().setGameState(GameStates.END);
        }
        // Decrement roundTime and time
        roundTime--;
        time--;
    }

    /**
     * Retrieves the total remaining time for the game.
     *
     * @return The total remaining time for the game.
     */
    private static int getTime() {
        return time;
    }

    /**
     * Retrieves the remaining time for the current game round.
     *
     * @return The remaining time for the current game round.
     */
    private static int getRoundTime() {
        return roundTime;
    }
}
