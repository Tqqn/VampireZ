package com.tqqn.minigames.modules.team.teams;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.team.TeamModule;

/**
 * Represents the Humans team in the game.
 */
public final class Humans extends AbstractTeam {

    /**
     * Constructs a new Humans team instance.
     *
     * @param databaseModule The DatabaseModule associated with this team.
     */
    public Humans(DatabaseModule databaseModule) {
        super("Humans", "<yellow>", "<yellow>[H]", databaseModule.getDefaultConfig().getSpawn("survivors"));
    }

    /**
     * Provides the kit for a player in the Humans team.
     *
     * @param playerModel The PlayerModel of the player to whom the kit is given.
     */
    @Override
    public void giveKit(PlayerModel playerModel) {
        // Implementation specific to giving a kit to a player in the Humans team - Currently empty, will add later TODO
    }
}
