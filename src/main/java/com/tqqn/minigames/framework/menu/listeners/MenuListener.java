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

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Menu matchedMenu = menuModule.matchMenu(event.getWhoClicked().getUniqueId());

        if (matchedMenu == null) return;
        matchedMenu.handleClick(event);
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        // Unregister menu - it has been closed.
        menuModule.unregisterMenu(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        // Unregister menu - the player has quit.
        menuModule.unregisterMenu(event.getPlayer().getUniqueId());
    }
}
