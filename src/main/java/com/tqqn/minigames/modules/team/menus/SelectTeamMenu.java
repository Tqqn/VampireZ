package com.tqqn.minigames.modules.team.menus;

import com.tqqn.minigames.framework.menu.Menu;
import com.tqqn.minigames.framework.menu.MenuButton;
import com.tqqn.minigames.modules.menu.MenuModule;
import com.tqqn.minigames.modules.player.PlayerModule;
import com.tqqn.minigames.modules.team.TeamModule;
import com.tqqn.minigames.modules.team.teams.Humans;
import com.tqqn.minigames.modules.team.teams.Vampires;
import com.tqqn.minigames.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public final class SelectTeamMenu extends Menu {

    public SelectTeamMenu(MenuModule menuModule) {
        super("&cSelect your team!", 3, menuModule);

        registerFillerItem(FillerType.BORDER, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName("").build());

        registerButton(new MenuButton(new ItemBuilder(Material.PLAYER_HEAD, 1).setDisplayName("<yellow>Humans").build()).setWhoClicked(clicked -> {
            TeamModule teamModule = (TeamModule) menuModule.getPlugin().getModuleManager().getModule(TeamModule.class);
            teamModule.addPlayerToTeam(PlayerModule.getPlayerModel(clicked.getUniqueId()), Humans.class, true);
            Bukkit.getScheduler().runTaskLater(menuModule.getPlugin(), () -> clicked.closeInventory(), 1L);
        }), 12);

        registerButton(new MenuButton(new ItemBuilder(Material.WITHER_SKELETON_SKULL, 1).setDisplayName("<red>Vampires").build()).setWhoClicked(clicked -> {
            TeamModule teamModule = (TeamModule) menuModule.getPlugin().getModuleManager().getModule(TeamModule.class);
            teamModule.addPlayerToTeam(PlayerModule.getPlayerModel(clicked.getUniqueId()), Vampires.class, true);
            Bukkit.getScheduler().runTaskLater(menuModule.getPlugin(), () -> clicked.closeInventory(), 1L);
        }), 14);
    }
}
