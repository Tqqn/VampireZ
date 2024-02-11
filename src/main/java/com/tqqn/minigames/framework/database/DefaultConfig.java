package com.tqqn.minigames.framework.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.modules.database.DatabaseModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class DefaultConfig {

    private final DatabaseModule databaseModule;
    private final VampireZ plugin;

    public DefaultConfig(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
        plugin = databaseModule.getPlugin();
        plugin.saveDefaultConfig();
    }

    public boolean isSetupMode() {
        return plugin.getConfig().getBoolean("setup-mode");
    }

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
