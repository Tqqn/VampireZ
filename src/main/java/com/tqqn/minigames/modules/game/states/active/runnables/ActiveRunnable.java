package com.tqqn.minigames.modules.game.states.active.runnables;

import com.tqqn.minigames.framework.game.AbstractGameState;
import org.bukkit.scheduler.BukkitRunnable;

public class ActiveRunnable extends BukkitRunnable {

    private final AbstractGameState state;

    public ActiveRunnable(AbstractGameState state) {
        this.state = state;
    }
    @Override
    public void run() {

    }
}
