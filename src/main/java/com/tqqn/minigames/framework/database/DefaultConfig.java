package com.tqqn.minigames.framework.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.modules.database.DatabaseModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class DefaultConfig {

    private final DatabaseModule databaseModule; // Database Module Instance
    private final VampireZ plugin; // Plugin Instance

    /**
     * Constructs a new DefaultConfig object with the specified DatabaseModule.
     *
     * @param databaseModule The DatabaseModule instance.
     */
    public DefaultConfig(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
        plugin = databaseModule.getPlugin();
        plugin.saveDefaultConfig();
    }

    /**
     * Checks if the plugin is in setup mode.
     * @return True if the plugin is in setup mode, false otherwise.
     */
    public boolean isSetupMode() {
        return plugin.getConfig().getBoolean("setup-mode");
    }

    /**
     * Retrieves the spawn location specified by the given spawn name.
     *
     * @param spawn The name of the spawn location.
     * @return The Location object representing the spawn location, or null if the world is not found.
     */
    public Location getSpawn(String spawn) {
            if (Bukkit.getWorld(plugin.getConfig().getString("spawns." + spawn + ".world")) == null) {
                Bukkit.getLogger().warning("DefaultConfig: World is null for " + spawn);
                return null;
            }
            return new Location(Bukkit.getWorld(plugin.getConfig().getString("spawns." + spawn + ".world")),
                    plugin.getConfig().getDouble("spawns." + spawn + ".x"),
                    plugin.getConfig().getDouble("spawns." + spawn + ".y"),
                    plugin.getConfig().getDouble("spawns." + spawn + ".z"),
                    (float) plugin.getConfig().getDouble("spawns." + spawn + ".yaw"),
                    (float) plugin.getConfig().getDouble("spawns." + spawn + ".pitch"));
    }
}
