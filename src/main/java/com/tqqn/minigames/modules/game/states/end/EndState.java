package com.tqqn.minigames.modules.game.states.end;

import com.tqqn.minigames.framework.game.AbstractGameState;
import com.tqqn.minigames.modules.game.GameModule;
import org.bukkit.event.Listener;

import java.util.Collection;

public class EndState extends AbstractGameState {
    public EndState(GameModule gameModule) {
        super(gameModule, "End");
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }
}
