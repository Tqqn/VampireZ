package com.tqqn.minigames.framework.menu;

import com.tqqn.minigames.modules.menu.MenuModule;
import com.tqqn.minigames.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Menu {

    private final MenuModule menuModule;
    private final Inventory inventory;
    private final Map<Integer, MenuButton> buttonMap;

    /**
     * Creates a new Menu Object.
     * @param title String
     * @param rows int
     */
    public Menu(String title, int rows, MenuModule menuModule) {
        this.menuModule = menuModule;
        if (rows > 6 || rows < 1 || title.length() > 32) {
            throw new IllegalArgumentException("Invalid arguments passed to menu constructor.");
        }
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatUtils.translateLegacy(title));
        this.buttonMap = new HashMap<>();
    }

    public void registerFillerItem(FillerType fillerType, ItemStack itemStack) {
        switch (fillerType) {
            case BORDER -> {
                // Top side
                for (int i = 0; i < 9; i++) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
                // Right side
                for (int i = 8; i < inventory.getSize(); i += 9) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
                // Left side
                for (int i = 0; i < inventory.getSize(); i += 9) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
                // Bottom side
                for (int i = inventory.getSize() -9; i < inventory.getSize(); i++) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
            }
            case BOTTOM -> {
                for (int i = inventory.getSize() -9; i < inventory.getSize(); i++) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
            }
            case FULL -> {
                for (int i = 0; i < inventory.getSize(); i++) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
            }
            case LEFT -> {
                for (int i = 0; i < inventory.getSize(); i += 9) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
            }
            case RIGHT -> {
                for (int i = 8; i < inventory.getSize(); i += 9) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
            }
            case TOP -> {
                for (int i = 0; i < 9; i++) {
                    buttonMap.put(i, new MenuButton(itemStack));
                }
            }
        }
    }

    /**
     * Void Method that will register a new MenuButton.
     * @param button MenuButton
     * @param slot int
     */
    public void registerButton(MenuButton button, int slot) {
        buttonMap.put(slot, button);
    }



    /**
     * Void Method that will handle the button click.
     * @param event InventoryClickEvent
     */
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null) return;
        if (!buttonMap.containsKey(event.getRawSlot())) return;

        Consumer<Player> consumer = buttonMap.get(event.getRawSlot()).getWhoClicked();

        if (consumer == null) return;

        consumer.accept((Player) event.getWhoClicked());
    }

    /**
     * Void Method that will handle/create the inventory.
     * @param player Player
     */
    public void open(Player player) {

        buttonMap.forEach((slot, button) -> inventory.setItem(slot, button.getItemStack()));

        player.openInventory(inventory);
        menuModule.registerMenu(player.getUniqueId(), this);
    }

    public enum FillerType {
        BORDER,
        BOTTOM,
        FULL,
        LEFT,
        RIGHT,
        TOP;
    }
}
