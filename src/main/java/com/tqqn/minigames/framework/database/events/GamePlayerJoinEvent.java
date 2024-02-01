package com.tqqn.minigames.framework.database.events;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GamePlayerJoinEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final PlayerModel player;

    private boolean cancelled = false;

    /**
     * Use GamePlayerJoinEvent and not the spigot/bukkit one, as of this way we can guarantee that a plugin player object is existing/made.
     * @param playerModel custom player object
     */
    public GamePlayerJoinEvent(PlayerModel playerModel) {
        super(true);
        this.player = playerModel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
