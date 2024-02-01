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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class PlayerModule extends AbstractModule {

    private static final List<PlayerModel> CACHED_PLAYERS = Collections.synchronizedList(new ArrayList<>());
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

    public static PlayerModel getPlayerModel(UUID uuid) {
        return CACHED_PLAYERS.stream().filter(playerModel -> playerModel.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public boolean processLogin(Player player) {
        player.getInventory().clear();

        player.teleport(databaseModule.getDefaultConfig().getSpawn("lobby"));
        PlayerModel playerModel;

        try {
            playerModel = databaseModule.loadPlayer(player.getUniqueId(), player.getName()).get();
        } catch (Exception ignored) {
            return false;
        }

        CACHED_PLAYERS.add(playerModel);

        Bukkit.getLogger().info(playerModel.getName());
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.HUMAN_KILLS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.HUMAN_DEATHS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.HUMAN_WINS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.VAMPIRE_KILLS)));
        Bukkit.getLogger().info(String.valueOf(playerModel.getStats().getStat(PlayerStats.StatType.VAMPIRE_LOSSES)));

        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(MessageUtil.PLAYER_JOIN.getMessage("<red>", player.getName(), "1", "1")));
        return true;
    }

    public void setSpectator(PlayerModel playerModel) {
        playerModel.setSpectator(true);
        Player player = Bukkit.getPlayer(playerModel.getUuid());
        if (!player.isOnline()) return;
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2000000, 1));
        Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(player));
    }
}
