package com.tqqn.minigames.framework.game.events;

import com.tqqn.minigames.framework.game.GameStates;
import com.tqqn.minigames.modules.game.GameModule;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class SwitchGameRoundEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final GameStates gameState;
    private final GameModule.GameRounds currentRound;
    private final GameModule.GameRounds nextRound;

    public SwitchGameRoundEvent(GameStates gameStates, GameModule.GameRounds currentRound, GameModule.GameRounds nextRound) {
        this.gameState = gameStates;
        this.currentRound = currentRound;
        this.nextRound = nextRound;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
