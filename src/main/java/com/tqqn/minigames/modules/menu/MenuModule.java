package com.tqqn.minigames.modules.menu;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.menu.Menu;
import com.tqqn.minigames.framework.menu.listeners.MenuListener;
import com.tqqn.minigames.modules.team.menus.SelectTeamMenu;

import java.util.*;

/**
 * MenuModule class extends AbstractModule and manages menu-related functionalities.
 * It handles menu registration, retrieval, and interaction.
 */
public final class MenuModule extends AbstractModule {

    private static Map<Class<? extends Menu>, Menu> loadedMenus;

    private Map<UUID, Menu> openMenus;

    /**
     * Constructor to initialize MenuModule with the specified parameters.
     *
     * @param plugin The VampireZ plugin instance.
     */
    public MenuModule(VampireZ plugin) {
        super(plugin, "Menu");
    }

    /**
     * Handles enabling logic for the menu module.
     * It sets up the menu listener, initializes loaded menus, and initializes openMenus map.
     */
    @Override
    public void onEnable() {
        setListeners(List.of(new MenuListener(this)));
        init();

        loadedMenus = new HashMap<>();
        loadedMenus.put(SelectTeamMenu.class, new SelectTeamMenu(this));

        openMenus = new HashMap<>();
    }

    /**
     * Handles disabling logic for the menu module.
     * It clears the openMenus map.
     */
    @Override
    public void onDisable() {
        openMenus.clear();
    }

    /**
     * Retrieves a menu based on its class.
     *
     * @param menuClass The class of the menu to retrieve.
     * @return The menu instance associated with the given class.
     */
    public static Menu getMenu(Class<? extends Menu> menuClass) {
        return loadedMenus.get(menuClass);
    }

    /**
     * Registers a menu for a specific player.
     *
     * @param toRegister The UUID of the player to register the menu for.
     * @param menu       The menu to register.
     */
    public void registerMenu(UUID toRegister, Menu menu) {
        openMenus.put(toRegister, menu);
    }

    /**
     * Unregisters a menu for a specific player.
     *
     * @param toUnRegister The UUID of the player to unregister the menu for.
     */
    public void unregisterMenu(UUID toUnRegister) {
        openMenus.remove(toUnRegister);
    }

    /**
     * Matches an open menu for a specific player.
     *
     * @param user The UUID of the player to match the menu for.
     * @return The matched menu instance for the player.
     */
    public Menu matchMenu(UUID user) {
        return openMenus.get(user);
    }
}
