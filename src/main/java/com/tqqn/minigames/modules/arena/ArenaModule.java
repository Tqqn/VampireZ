package com.tqqn.minigames.modules.arena;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.arena.Arena;
import com.tqqn.minigames.modules.database.DatabaseModule;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ArenaModule extends AbstractModule {

    private final List<Arena> loadedArenas = new ArrayList<>();
    private final DatabaseModule databaseModule;

    /**
     * Constructor to initialize a module with specified parameters.
     *
     * @param plugin The VampireZ plugin instance.
     */
    public ArenaModule(VampireZ plugin) {
        super(plugin, "Arena");
        databaseModule = (DatabaseModule) plugin.getModuleManager().getModule(DatabaseModule.class);
    }

    /**
     * Abstract method to be implemented to handle module enable logic.
     */
    @Override
    public void onEnable() {
        init();
        //initNewArena(databaseModule);
    }

    /**
     * Abstract method to be implemented to handle module disable logic.
     */
    @Override
    public void onDisable() {
        for (Arena arena : loadedArenas) {
            arena.remove();
        }
        loadedArenas.clear();
    }

    public void initNewArena(int minimumPlayers, int maximumPlayers, Location lobbyLocation) {
        Arena newArena = new Arena(minimumPlayers, maximumPlayers, lobbyLocation);
        loadedArenas.add(newArena);
    }
}
