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

/**
 * Represents a menu for selecting a team.
 */
public final class SelectTeamMenu extends Menu {

    /**
     * Constructs a new SelectTeamMenu instance.
     *
     * @param menuModule The MenuModule associated with this menu.
     */
    public SelectTeamMenu(MenuModule menuModule) {
        super("&cSelect your team!", 3, menuModule);

        // Register filler item
        registerFillerItem(FillerType.BORDER, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName("").build());

        // Register button for selecting the "Humans" team
        registerButton(new MenuButton(new ItemBuilder(Material.PLAYER_HEAD, 1).setDisplayName("<yellow>Humans").build()).setWhoClicked(clicked -> {
            // Add the player to the Humans team
            TeamModule teamModule = (TeamModule) menuModule.getPlugin().getModuleManager().getModule(TeamModule.class);
            teamModule.addPlayerToTeam(PlayerModule.getPlayerModel(clicked.getUniqueId()), Humans.class, true);
            // Close the inventory after a short delay
            Bukkit.getScheduler().runTaskLater(menuModule.getPlugin(), () -> clicked.closeInventory(), 1L);
        }), 12);

        // Register button for selecting the "Vampires" team
        registerButton(new MenuButton(new ItemBuilder(Material.WITHER_SKELETON_SKULL, 1).setDisplayName("<red>Vampires").build()).setWhoClicked(clicked -> {
            // Add the player to the Vampires team
            TeamModule teamModule = (TeamModule) menuModule.getPlugin().getModuleManager().getModule(TeamModule.class);
            teamModule.addPlayerToTeam(PlayerModule.getPlayerModel(clicked.getUniqueId()), Vampires.class, true);
            // Close the inventory after a short delay
            Bukkit.getScheduler().runTaskLater(menuModule.getPlugin(), () -> clicked.closeInventory(), 1L);
        }), 14);
    }
}
