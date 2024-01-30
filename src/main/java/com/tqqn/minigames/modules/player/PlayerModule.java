package com.tqqn.minigames.modules.player;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.utils.MessageUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerModule extends AbstractModule {

    @Getter private final List<PlayerModel> players = new ArrayList<>();
    private final DatabaseModule databaseModule;

    public PlayerModule(VampireZ plugin) {
        super(plugin, Arrays.asList(

        ), "Player");
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
        return players.stream().filter(playerModel -> playerModel.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void processLogin(Player player) {
        player.getInventory().clear();

        player.teleport(databaseModule.getDefaultConfig().getSpawn("lobby"));

        PlayerModel playerModel = new PlayerModel(player.getUniqueId(), player.getName());
        this.players.add(playerModel);

        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(MessageUtil.PLAYER_JOIN.getMessage("<red>", player.getName(), "1", "1")));
    }
}
