package com.tqqn.minigames.modules.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.DefaultConfig;
import com.tqqn.minigames.framework.database.config.AbstractConfig;
import com.tqqn.minigames.framework.database.listeners.PlayerJoinListener;
import com.tqqn.minigames.framework.database.listeners.PlayerQuitListener;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.configs.PlayerConfig;
import com.tqqn.minigames.modules.player.PlayerModule;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class DatabaseModule extends AbstractModule {

    private static DefaultConfig defaultConfig;
    private final Map<String, AbstractConfig> loadedCustomConfigs;

    public DatabaseModule(VampireZ plugin) {
        super(plugin, "Database");

        setListeners(Arrays.asList(
                new PlayerJoinListener(this),
                new PlayerQuitListener(this)));

        defaultConfig = new DefaultConfig(this);
        loadedCustomConfigs = new HashMap<>();
        loadedCustomConfigs.put("player.yml", new PlayerConfig(this, "player.yml"));
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Bukkit.getLogger().info("Saving player: " + player.getName() + "!");
            savePlayer(PlayerModule.getPlayerModel(player.getUniqueId()));
            player.kick();
            Bukkit.getLogger().info("Finished saving player: " + player.getName() + "! Kicked player!");
        });
        disable();
    }

    public void savePlayer(PlayerModel playerModel) {
        CompletableFuture.runAsync(() -> {
            PlayerConfig playerConfig = (PlayerConfig) loadedCustomConfigs.get("player.yml");
            playerConfig.savePlayer(playerModel);
        });
    }

    public CompletableFuture<PlayerModel> loadPlayer(UUID uuid, String name) {
        PlayerConfig playerConfig = (PlayerConfig) loadedCustomConfigs.get("player.yml");
        return CompletableFuture.supplyAsync(() -> new PlayerModel(uuid, name, playerConfig.getStats(uuid)));
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
