package com.tqqn.minigames.framework.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.listeners.PlayerJoinListener;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Getter
public class DatabaseModule extends AbstractModule {

    private File playerConfigFile;
    private FileConfiguration playerConfig;
    private static PluginConfig pluginConfig;

    public DatabaseModule(VampireZ plugin) {
        super(plugin, Arrays.asList(
                new PlayerJoinListener()));
        pluginConfig = new PluginConfig();
    }

    @Override
    protected void onEnable() {
        getPlugin().saveDefaultConfig();
        registerListeners();
    }

    @Override
    protected void onDisable() {
        unRegisterListeners();
    }


    private void createCustomConfig() {
        playerConfigFile = new File(getPlugin().getDataFolder(), "players.yml");
        if (!playerConfigFile.exists()) {
            playerConfigFile.getParentFile().mkdirs();
            getPlugin().saveResource("players.yml", false);
        }

        playerConfig = new YamlConfiguration();

        try {
            playerConfig.load(playerConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerConfig(FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(playerConfigFile);
        } catch (IOException ignored) {}
    }

    public static CompletableFuture savePlayer(PlayerModel playerModel) {
        return CompletableFuture.runAsync(() -> pluginConfig.notify());
    }

    public FileConfiguration getDefaultConfig() {
        return getPlugin().getConfig();
    }
}
