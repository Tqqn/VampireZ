package com.tqqn.minigames.modules.menu;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.menu.Menu;
import com.tqqn.minigames.framework.menu.listeners.MenuListener;
import com.tqqn.minigames.modules.team.menus.SelectTeamMenu;

import java.util.*;

public final class MenuModule extends AbstractModule {

    private static Map<Class<? extends Menu>, Menu> loadedMenus;

    private Map<UUID, Menu> openMenus;

    public MenuModule(VampireZ plugin) {
        super(plugin, "Menu");
    }

    @Override
    public void onEnable() {
        setListeners(List.of(new MenuListener(this)));
        init();

        loadedMenus = new HashMap<>();
        loadedMenus.put(SelectTeamMenu.class, new SelectTeamMenu(this));

        openMenus = new HashMap<>();
    }

    @Override
    public void onDisable() {
        openMenus.clear();
    }

    public static Menu getMenu(Class<? extends Menu> menuClass) {
        return loadedMenus.get(menuClass);
    }



    public void registerMenu(UUID toRegister, Menu menu) {
        openMenus.put(toRegister, menu);
    }

    public void unregisterMenu(UUID toUnRegister) {
        openMenus.remove(toUnRegister);
    }

    public Menu matchMenu(UUID user) {
        return openMenus.get(user);
    }
}
