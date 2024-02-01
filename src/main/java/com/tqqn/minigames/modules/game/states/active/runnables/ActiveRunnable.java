package com.tqqn.minigames.modules.game.states.active.runnables;

import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.framework.game.events.SwitchGameRoundEvent;
import com.tqqn.minigames.modules.game.GameModule;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ActiveRunnable extends BukkitRunnable {

    private final AbstractGameState state;
    private static int time = 560;
    private static int roundTime = 60;

    public ActiveRunnable(AbstractGameState state) {
        this.state = state;
    }
    @Override
    public void run() {

        if (roundTime == 0) {
            GameModule.GameRounds nextRound = GameModule.getCurrentRound().getNext();
            Bukkit.getPluginManager().callEvent(new SwitchGameRoundEvent(GameModule.getGameState(), GameModule.getCurrentRound(), nextRound));
            roundTime = 60;
        }
        if (time == 0) {
            cancel();
            state.getGameModule().setGameState(GameStates.END);
        }

        roundTime--;
        time--;
    }

    private static int getTime() {
        return time;
    }

    private static int getRoundTime() {
        return roundTime;
    }
}
