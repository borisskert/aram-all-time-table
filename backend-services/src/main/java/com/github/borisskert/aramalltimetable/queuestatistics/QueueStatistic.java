package com.github.borisskert.aramalltimetable.queuestatistics;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueueStatistic {

    private Integer victories;
    private Integer defeats;
    private Integer games;

    public Integer getVictories() {
        return victories;
    }

    public void setVictories(Integer victories) {
        this.victories = victories;
    }

    public Integer getDefeats() {
        return defeats;
    }

    public void setDefeats(Integer defeats) {
        this.defeats = defeats;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    @JsonProperty("winRate")
    public Double getWinRate() {
        return ((double)victories / (double)games) * 100.0;
    }
}
