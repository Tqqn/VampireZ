package com.tqqn.minigames.modules.team.teams;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;
import com.tqqn.minigames.modules.database.DatabaseModule;

/**
 * Represents the Vampires team in the game.
 */
public final class Vampires extends AbstractTeam {

    /**
     * Constructs a new Vampires team instance.
     *
     * @param databaseModule The DatabaseModule associated with this team.
     */
    public Vampires(DatabaseModule databaseModule) {
        super("Vampires", "<red>", "<red>[V]", databaseModule.getDefaultConfig().getSpawn("vampires"));
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
