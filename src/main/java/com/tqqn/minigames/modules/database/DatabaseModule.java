package com.tqqn.minigames.modules.database;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.DefaultConfig;
import com.tqqn.minigames.framework.database.listeners.PlayerJoinListener;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Getter
public class DatabaseModule extends AbstractModule {

    private static DefaultConfig defaultConfig;

    public DatabaseModule(VampireZ plugin) {
        super(plugin, Arrays.asList(
                new PlayerJoinListener()));

        defaultConfig = new DefaultConfig(this);
    }

    @Override
    public void onEnable() {
        registerListeners();
    }

    @Override
    public void onDisable() {
        unRegisterListeners();
    }


    public static CompletableFuture savePlayer(PlayerModel playerModel) {
        return CompletableFuture.runAsync(() -> defaultConfig.notify());
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
