package com.tqqn.minigames.framework.database.models;

import lombok.Getter;

import java.util.HashMap;

public final class PlayerStats {

    /**
     * The map storing different types of player statistics.
     */
    private final HashMap<StatType, Integer> stats;


    /**
     * Constructs a new PlayerStats object with the specified initial statistics.
     *
     * @param humanKills    The number of kills as a human.
     * @param humanWins     The number of wins as a human.
     * @param humanLosses   The number of losses as a human.
     * @param humanDeaths   The number of deaths as a human.
     * @param vampireKills  The number of kills as a vampire.
     * @param vampireWins   The number of wins as a vampire.
     * @param vampireLosses The number of losses as a vampire.
     * @param vampireDeaths The number of deaths as a vampire.
     */
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

    /**
     * Retrieves the value of the specified statistic type.
     *
     * @param statType The type of statistic to retrieve.
     * @return The value of the specified statistic.
     */
    public int getStat(StatType statType) {
        return stats.get(statType);
    }

    /**
     * Increases the value of the specified statistic type by 1.
     *
     * @param statType The type of statistic to increase.
     */
    public void increaseStat(StatType statType) {
        stats.put(statType, stats.get(statType)+1);
    }

    /**
     * Enum of different types of player statistics.
     */
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

        /**
         * Constructs a new StatType object with the specified configuration path.
         *
         * @param path The configuration path associated with the statistic type.
         */
        StatType(String path) {
            this.configPath = path;
        }
    }
}
