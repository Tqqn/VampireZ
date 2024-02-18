package com.tqqn.minigames.framework.menu.listeners;

import com.tqqn.minigames.framework.menu.Menu;
import com.tqqn.minigames.modules.menu.MenuModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class MenuListener implements Listener {

    private final MenuModule menuModule;

    public MenuListener(MenuModule menuModule) {
        this.menuModule = menuModule;
    }

    /**
     * Handles the InventoryClickEvent.
     * Matches the clicked inventory with a menu and handles the click action.
     */
    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Menu matchedMenu = menuModule.matchMenu(event.getWhoClicked().getUniqueId());

        if (matchedMenu == null) return;
        matchedMenu.handleClick(event);
    }

    /**
     * Handles the InventoryCloseEvent.
     * Unregisters the menu associated with the closed inventory.
     */
    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        menuModule.unregisterMenu(event.getPlayer().getUniqueId());
    }

    /**
     * Handles the PlayerQuitEvent.
     * Unregisters the menu associated with the player who quit.
     */
    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        menuModule.unregisterMenu(event.getPlayer().getUniqueId());
    }
}
