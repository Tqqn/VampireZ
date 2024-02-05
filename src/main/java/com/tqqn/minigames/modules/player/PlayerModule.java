package com.tqqn.minigames.modules.player;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.AbstractModule;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.database.configs.PlayerConfig;
import com.tqqn.minigames.utils.MessageUtil;
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

    public void processFirstLogin(Player player) {
        PlayerConfig playerConfig = (PlayerConfig) databaseModule.getLoadedCustomConfigs().get("players.yml");
        playerConfig.createPlayerTemplate(player.getUniqueId(), player.getName());
    }

    public void processLogin(Player player) {
        try {
            if (!databaseModule.doesPlayerExist(player.getUniqueId()).get()) {
                processFirstLogin(player);
            }
        } catch (ExecutionException | InterruptedException e) {
            Bukkit.getLogger().info("Something went wrong getting the playerdata of " + player.getName());
            player.kickPlayer("Something went wrong getting your playerdata! Try it again later.");
        }

        PlayerModel playerModel;

        try {
            playerModel = databaseModule.loadPlayer(player.getUniqueId(), player.getName()).get();
        } catch (Exception ignored) {
            return;
        }

        CACHED_PLAYERS.add(playerModel);

        player.getInventory().clear();

        player.teleport(databaseModule.getDefaultConfig().getSpawn("lobby"));

        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(MessageUtil.PLAYER_JOIN.getMessage("<red>", player.getName(), "1", "1")));
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
