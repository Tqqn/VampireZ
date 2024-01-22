package com.tqqn.minigames.framework.database.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PlayerModel {

    private final UUID uuid;
    private final String name;

    public PlayerModel(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
