package com.tqqn.minigames.modules.nms.v1_20_R3;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.modules.nms.ReflectionLayer;
import com.tqqn.minigames.modules.player.PlayerModule;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.scores.PlayerTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class v1_20_R3 implements ReflectionLayer {

    private final Map<UUID, Entity> loadedNMSBats = new HashMap<>();
    private Scoreboard mainScoreboard;

    public v1_20_R3(VampireZ plugin) {
        Bukkit.getScheduler().runTask(plugin, () -> mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @Override
    public String getVersionString() {
        return "1.20.4";
    }

    @Override
    public void sendPacket(Player player, Object packetObject) {
        Packet packet = (Packet) packetObject;
        ((CraftPlayer)player).getHandle().connection.send(packet);
    }

    @Override
    public void disguisePlayerBat(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer serverPlayer = craftPlayer.getHandle();

        MutableComponent component = Component.empty().append("[V] " + player.getName());
        ChatFormatting color = ChatFormatting.RED;

        component.withStyle(color);

        Bat entityBat = new Bat(EntityType.BAT,serverPlayer.level());
        entityBat.setCustomName(component);
        entityBat.setPos(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        entityBat.setResting(false);

        Bukkit.getOnlinePlayers().forEach(players -> {
            if (players == player) return;
            sendPacket(players, new ClientboundAddEntityPacket(entityBat));
            sendPacket(players, new ClientboundSetEntityDataPacket(entityBat.getId(), entityBat.getEntityData().packDirty()));
            sendPacket(players, new ClientboundRotateHeadPacket(entityBat, (byte) player.getLocation().getYaw()));
            sendPacket(players, new ClientboundMoveEntityPacket.Rot(entityBat.getId(), (byte) player.getLocation().getYaw(), (byte) player.getLocation().getPitch(), false));
        });

        loadedNMSBats.put(player.getUniqueId(), entityBat);
        PlayerModule.getPlayerModel(player.getUniqueId()).setBat(true);
        player.setInvisible(true);
    }

    public void moveBat(Player player) {
        Location location = player.getLocation();
        Bat entity = (Bat) loadedNMSBats.get(player.getUniqueId());
        entity.setYHeadRot(player.getLocation().getYaw());
        entity.setPos(location.getX(), location.getY(), location.getZ());
        Bukkit.getOnlinePlayers().forEach(players -> {
            sendPacket(players, new ClientboundTeleportEntityPacket(entity));
            sendPacket(players, new ClientboundRotateHeadPacket(entity, (byte) player.getLocation().getYaw()));
            sendPacket(players, new ClientboundMoveEntityPacket.Rot(entity.getId(), (byte) location.getYaw(), (byte) location.getPitch(), false));
        });
    }

    public void removeBat(Player player) {
        ClientboundRemoveEntitiesPacket remove = new ClientboundRemoveEntitiesPacket(loadedNMSBats.get(player.getUniqueId()).getId());
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (players == player) return;
            sendPacket(players, remove);
        });
        PlayerModule.getPlayerModel(player.getUniqueId()).setBat(false);
        player.setInvisible(false);
    }

    public void sendNameTag(Player player, String teamName, String color, String prefix, String suffix) {
        CraftScoreboard craftScoreboard = (CraftScoreboard) mainScoreboard;
        PlayerTeam playerTeam = new PlayerTeam(craftScoreboard.getHandle(), teamName);

        playerTeam.setColor(ChatFormatting.getByName(color));
        playerTeam.setPlayerPrefix(CraftChatMessage.fromStringOrNull(prefix + " "));
        playerTeam.setPlayerSuffix(CraftChatMessage.fromStringOrNull(suffix));
        ClientboundSetPlayerTeamPacket add = ClientboundSetPlayerTeamPacket.createPlayerPacket(playerTeam, player.getName(), ClientboundSetPlayerTeamPacket.Action.ADD);
        Bukkit.getLogger().info(playerTeam.toString());
        Bukkit.getOnlinePlayers().forEach(players -> sendPacket(players, add));
    }

    public void initScoreboardTeams() {
        Team vampireTeam = mainScoreboard.getTeam("100_vampires");
        if (vampireTeam == null) {
            vampireTeam = mainScoreboard.registerNewTeam("100_vampires");
        }

        Team humanTeam = mainScoreboard.getTeam("99_humans");
        if (humanTeam == null) {
            humanTeam = mainScoreboard.registerNewTeam("99_humans");
        }

        vampireTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        humanTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }
}
