package com.github.borisskert.aramalltimetable.riot.model;

import java.util.List;

public class MatchList {

    private List<MatchReference> matches;
    private Integer startIndex;
    private Integer endIndex;
    private Integer totalGames;

    public List<MatchReference> getMatches() {
        return matches;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public Integer getTotalGames() {
        return totalGames;
    }

}
