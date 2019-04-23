package com.github.borisskert.aramalltimetable.riot.model;

public class MatchReference {
    private Long gameId;
    private Integer champion;
    private String season;
    private Long timestamp;

    public Long getGameId() {
        return gameId;
    }

    public Integer getChampion() {
        return champion;
    }

    public String getSeason() {
        return season;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
