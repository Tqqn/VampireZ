package com.tqqn.minigames.modules.team.teams;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;

public class Vampires extends AbstractTeam {

    public Vampires() {
        super("Vampires", "<red>", "<red>[V]");
    }

    @Override
    public void giveKit(PlayerModel playerModel) {

    }
}
