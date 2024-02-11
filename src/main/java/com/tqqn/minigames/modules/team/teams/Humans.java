package com.tqqn.minigames.modules.team.teams;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;
import com.tqqn.minigames.modules.database.DatabaseModule;
import com.tqqn.minigames.modules.team.TeamModule;

public final class Humans extends AbstractTeam {

    public Humans(DatabaseModule databaseModule) {
        super("Humans", "<yellow>", "<yellow>[H]", databaseModule.getDefaultConfig().getSpawn("survivors"));
    }

    @Override
    public void giveKit(PlayerModel playerModel) {

    }
}
