package com.tqqn.minigames.framework.database.models;

import java.util.HashMap;

public class PlayerStats {

    private final HashMap<StatType, Integer> stats;


    public PlayerStats(int humanKills, int humanWins, int humanLosses, int humanDeaths, int vampireKills, int vampireWins, int vampireLosses, int vampireDeaths) {
        stats = new HashMap<>();
        stats.put(StatType.HUMAN_KILLS, humanKills);
        stats.put(StatType.HUMAN_WINS, humanWins);
        stats.put(StatType.HUMAN_LOSSES, humanLosses);
        stats.put(StatType.HUMAN_DEATHS, humanDeaths);
        stats.put(StatType.VAMPIRE_KILLS, vampireKills);
        stats.put(StatType.VAMPIRE_WINS, vampireWins);
        stats.put(StatType.VAMPIRE_LOSSES, vampireLosses);
        stats.put(StatType.VAMPIRE_DEATHS, vampireDeaths);
    }

    public int getStat(StatType statType) {
        return stats.get(statType);
    }

    public void increaseStat(StatType statType) {
        stats.put(statType, stats.get(statType)+1);
    }
    public enum StatType {
        HUMAN_KILLS,
        HUMAN_WINS,
        HUMAN_LOSSES,
        HUMAN_DEATHS,

        VAMPIRE_KILLS,
        VAMPIRE_WINS,
        VAMPIRE_LOSSES,
        VAMPIRE_DEATHS;
    }
}
