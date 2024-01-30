package com.tqqn.minigames.modules.game.states.active;

import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.modules.game.GameModule;
import com.tqqn.minigames.modules.game.states.active.runnables.ActiveRunnable;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.Collection;

public class ActiveState extends AbstractGameState {
    public ActiveState(GameModule gameModule) {
        super(gameModule, Arrays.asList(), "Lobby");
    }

    @Override
    public void onEnable() {
        ActiveRunnable activeRunnable = new ActiveRunnable(this);
    }

    @Override
    public void onDisable() {

    }
}
