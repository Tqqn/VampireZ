package com.tqqn.minigames.modules.player;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.Array;
import java.util.*;

public class PlayerModule extends AbstractModule {

    @Getter private final List<PlayerModel> players = new ArrayList<>();

    public PlayerModule(VampireZ plugin) {
        super(plugin, Arrays.asList(

        ));
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    public PlayerModel getPlayerModel(UUID uuid) {
        return players.stream().filter(playerModel -> playerModel.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void processLogin(Player player) {
        player.getInventory().clear();
        player.teleport(player.getLocation()); // TODO: lobby location

        PlayerModel playerModel = new PlayerModel(player.getUniqueId(), player.getName());
        this.players.add(playerModel);

        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage("")); // TODO: JoinMessage

    }
}
