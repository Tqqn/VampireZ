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

    public AbstractConfig(DatabaseModule databaseModule, String configName) {
        this.databaseModule = databaseModule;
        this.configName = configName;

        loadConfig();
    }

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

    public void saveCustomConfig(FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(customConfigFile);
        } catch (IOException ignored) {}
    }

    public void saveValueToConfig(String path, Object value) {
        getCustomConfig().set(path, value);
    }
}
