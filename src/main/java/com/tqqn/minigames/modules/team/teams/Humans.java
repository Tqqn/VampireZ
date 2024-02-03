package com.tqqn.minigames.modules.team.teams;

import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.team.AbstractTeam;

public class Humans extends AbstractTeam {

    public Humans() {
        super("Humans", "<yellow>", "<yellow>[H]");
    }

    @Override
    public void giveKit(PlayerModel playerModel) {

    }
}
