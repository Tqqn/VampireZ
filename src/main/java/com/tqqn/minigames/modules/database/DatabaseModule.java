package com.tqqn.minigames.modules.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.DefaultConfig;
import com.tqqn.minigames.framework.database.config.AbstractConfig;
import com.tqqn.minigames.framework.database.listeners.PlayerJoinListener;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.configs.PlayerConfig;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Getter
public class DatabaseModule extends AbstractModule {

    private static DefaultConfig defaultConfig;
    private final Map<String, AbstractConfig> loadedCustomConfigs;

    public DatabaseModule(VampireZ plugin) {
        super(plugin, Arrays.asList(
                new PlayerJoinListener()), "Database");

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
        disable();
    }

    public CompletableFuture savePlayer(PlayerModel playerModel) {
        return CompletableFuture.runAsync(() -> {
            loadedCustomConfigs.get("player.yml")
        }); //PLACEHOLDER
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
