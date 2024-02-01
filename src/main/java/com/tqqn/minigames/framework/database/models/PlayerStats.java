package com.tqqn.minigames.framework.database.models;

import lombok.Getter;

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

    @Getter
    public enum StatType {
        HUMAN_KILLS(".stats.human_kills"),
        HUMAN_WINS(".stats.human_wins"),
        HUMAN_LOSSES(".stats.human_losses"),
        HUMAN_DEATHS(".stats.human_deaths"),

        VAMPIRE_KILLS(".stats.vampire_kills"),
        VAMPIRE_WINS(".stats.vampire_wins"),
        VAMPIRE_LOSSES(".stats.vampire_losses"),
        VAMPIRE_DEATHS(".stats.vampire_deaths");

        private final String configPath;

        StatType(String path) {
            this.configPath = path;
        }
    }
}
