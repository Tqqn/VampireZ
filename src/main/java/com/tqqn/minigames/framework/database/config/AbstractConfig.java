package com.tqqn.minigames.framework.database.config;

import com.tqqn.minigames.modules.database.DatabaseModule;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class AbstractConfig {
    @Getter
    private final DatabaseModule databaseModule;

    // Local Variables
    private final String configName;
    private File customConfigFile;

    @Getter
    private FileConfiguration customConfig;

    /**
     * Abstract AbstractConfig Constructor
     *
     * @param databaseModule The DatabaseModule instance.
     * @param configName     The name of the configuration file.
     */
    public AbstractConfig(DatabaseModule databaseModule, String configName) {
        this.databaseModule = databaseModule;
        this.configName = configName;

        loadConfig();
    }

    /**
     * Loads the configuration file.
     */
    private void loadConfig() {
        customConfigFile = new File(databaseModule.getPlugin().getDataFolder(), configName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            databaseModule.getPlugin().saveResource(configName, false);
        }

        customConfig = new YamlConfiguration();

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Saves the custom configuration file.
     */
    public void saveCustomConfig() {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException ignored) {}
    }

    /**
     * Saves a value to the custom configuration file.
     *
     * @param path  The path where the value should be saved.
     * @param value The value to be saved.
     */
    public void saveValueToConfig(String path, Object value) {
        getCustomConfig().set(path, value);
    }
}
