package com.tqqn.minigames.modules.player;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.utils.MessageUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class PlayerModule extends AbstractModule {

    private final List<PlayerModel> CACHED_PLAYERS = Collections.synchronizedList(new ArrayList<>());
    private final DatabaseModule databaseModule;

    public PlayerModule(VampireZ plugin) {
        super(plugin, "Player");
        this.databaseModule = (DatabaseModule) getPlugin().getModuleManager().getModule(DatabaseModule.class);
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public PlayerModel getPlayerModel(UUID uuid) {
        return CACHED_PLAYERS.stream().filter(playerModel -> playerModel.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void processLogin(Player player) {
        player.getInventory().clear();

        player.teleport(databaseModule.getDefaultConfig().getSpawn("lobby"));
        PlayerModel playerModel;

        try {
            playerModel = databaseModule.loadPlayer(player.getUniqueId()).get();
        } catch (Exception ignored) {
            return;
        }

        CACHED_PLAYERS.add(playerModel);

        Bukkit.getLogger().info(playerModel.getName());
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.HUMAN_KILLS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.HUMAN_DEATHS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.HUMAN_WINS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.VAMPIRE_KILLS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.VAMPIRE_LOSSES)));

        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(MessageUtil.PLAYER_JOIN.getMessage("<red>", player.getName(), "1", "1")));
    }
}
