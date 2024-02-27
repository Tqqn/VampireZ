package com.tqqn.minigames.modules.nms.v1_20_R3;

import com.tqqn.minigames.VampireZ;
import com.tqqn.minigames.framework.scoreboard.PluginScoreboard;
import com.tqqn.minigames.modules.nms.ReflectionLayer;
import com.tqqn.minigames.modules.player.PlayerModule;
import io.papermc.paper.scoreboard.numbers.NumberFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * v1_20_R3 class implements the ReflectionLayer interface and provides implementation for
 * accessing NMS (net.minecraft.server) methods and functionalities specific to version 1.20.4.
 */
public class v1_20_R3 implements ReflectionLayer {

    private final Map<UUID, Entity> loadedNMSBats = new HashMap<>();
    private Scoreboard mainScoreboard;

    /**
     * Constructor to initialize v1_20_R3 with the specified plugin.
     *
     * @param plugin The VampireZ plugin instance.
     */
    public v1_20_R3(VampireZ plugin) {
        Bukkit.getScheduler().runTask(plugin, () -> mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard());
    }

    /**
     * Retrieves the version string of the Minecraft server.
     * This method returns a string representing the version of the Minecraft server,
     * such as "1.17.1" or "1.18".
     *
     * @return The version string of the Minecraft server.
     */
    @Override
    public String getVersionString() {
        return "1.20.4";
    }

    /**
     * Sends a packet to the specified player using server reflection.
     * This method sends a packet object to the given player using server reflection,
     * allowing for the manipulation of game entities and events at a low level.
     *
     * @param player The player to whom the packet is being sent.
     * @param packetObject The packet object to be sent to the player.
     */
    @Override
    public void sendPacket(Player player, Object packetObject) {
        Packet packet = (Packet) packetObject;
        ((CraftPlayer)player).getHandle().connection.send(packet);
    }

    /**
     * Disguises the specified player as a bat entity.
     * This method disguises the player as a bat entity by modifying game data and sending packets
     * to all players to reflect the disguise. The player will appear as a bat to other players
     * until the disguise is removed.
     *
     * @param player The player to disguise as a bat.
     */
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

    /**
     * Moves the disguised bat entity to the specified player's current location.
     * This method updates the bat entity's position and head rotation,
     * then sends packets to all online players to teleport the bat entity and update its rotation.
     *
     * @param player The player whose bat entity is being moved.
     */
    @Override
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

    /**
     * Removes the disguised bat entity from the game.
     * This method sends packets to all online players to remove the bat entity,
     * updates the player's bat status, and makes the player visible again.
     *
     * @param player The player whose bat entity is being removed.
     */
    @Override
    public void removeBat(Player player) {
        ClientboundRemoveEntitiesPacket remove = new ClientboundRemoveEntitiesPacket(loadedNMSBats.get(player.getUniqueId()).getId());
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (players == player) return;
            sendPacket(players, remove);
        });
        PlayerModule.getPlayerModel(player.getUniqueId()).setBat(false);
        player.setInvisible(false);
    }

    /**
     * Sends a custom name tag to the specified player.
     * This method creates a custom team with the specified name, color, prefix, and suffix,
     * adds the player to the team, and sends packets to all online players to display the custom name tag for the player.
     *
     * @param player   The player to whom the custom name tag is being sent.
     * @param teamName The name of the custom team.
     * @param color    The color of the name tag.
     * @param prefix   The prefix to be displayed before the player's name.
     * @param suffix   The suffix to be displayed after the player's name.
     */
    @Override
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

    /**
     * Initializes the scoreboard teams for vampires and humans.
     * If the teams do not exist, this method creates them and sets collision rules to NEVER
     * to prevent player collision within the teams.
     */
    @Override
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

    /**
     * DEBUG/TEST METHOD
     * Sends a sidebar scoreboard to the specified player.
     * This method creates a new scoreboard with a dummy objective, adds the player to a new team for the sidebar,
     * sets the display name of the objective, and sends packets to the player to display the sidebar scoreboard.
     *
     * @param player The player to whom the sidebar scoreboard is being sent.
     */
    @Override
    public void sendSideBarScoreboard(Player player) {
        net.minecraft.world.scores.Scoreboard nmsScoreboard = new net.minecraft.world.scores.Scoreboard();
        Objective objective = new Objective(nmsScoreboard, "test", ObjectiveCriteria.DUMMY, CraftChatMessage.fromStringOrNull("test"), ObjectiveCriteria.RenderType.INTEGER, false, null);
        nmsScoreboard.setDisplayObjective(DisplaySlot.SIDEBAR, objective);
        PlayerTeam playerTeam = new PlayerTeam(nmsScoreboard, "sidebar");
        nmsScoreboard.addPlayerTeam("sidebar");
        nmsScoreboard.addPlayerToTeam(player.getName(), playerTeam);
        objective.setDisplayName(CraftChatMessage.fromStringOrNull("§6LOLOL"));
        sendPacket(player, new ClientboundSetObjectivePacket(objective, 0));
        sendPacket(player, new ClientboundSetDisplayObjectivePacket(DisplaySlot.SIDEBAR, objective));
        sendPacket(player, ClientboundSetPlayerTeamPacket.createPlayerPacket(playerTeam, player.getName(), ClientboundSetPlayerTeamPacket.Action.ADD));
        sendPacket(player, new ClientboundSetScorePacket(player.getName(), "test", 1, CraftChatMessage.fromStringOrNull(player.getName()), null));
    }
}
