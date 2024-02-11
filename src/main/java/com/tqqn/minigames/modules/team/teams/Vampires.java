package com.tqqn.minigames.modules.team.teams;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;
import com.tqqn.minigames.modules.database.DatabaseModule;

public final class Vampires extends AbstractTeam {

    public Vampires(DatabaseModule databaseModule) {
        super("Vampires", "<red>", "<red>[V]", databaseModule.getDefaultConfig().getSpawn("vampires"));
    }

    @Override
    public void giveKit(PlayerModel playerModel) {
    }
}
