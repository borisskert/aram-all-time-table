package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.riot.model.Match;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SummonerMatches {
    private static final double PERCENT_MAX = 100.0;

    private final int maxConsideredMatches;

    private final String accountId;

    private final LinkedList<SummonerMatch> latestMatches = new LinkedList<>();
    private final List<SummonerMatch> victories = new ArrayList<>();
    private final List<SummonerMatch> defeats = new ArrayList<>();
    private Match latestMatch;
    private int winStreak;
    private int loseStreak;

    public SummonerMatches(int maxConsideredMatches, String accountId) {
        this.maxConsideredMatches = maxConsideredMatches;
        this.accountId = accountId;
    }

    public void addMatch(Match match) {
        latestMatch = match;

        SummonerMatch summonerMatch = new SummonerMatch(match, accountId);
        addSummonerMatch(summonerMatch);
    }

    private void addSummonerMatch(SummonerMatch match) {
        latestMatches.add(match);

        if (latestMatches.size() > maxConsideredMatches) {
            latestMatches.removeFirst();
        }

        if (match.isVictory()) {
            winStreak++;
            loseStreak = 0;
            victories.add(match);
        } else {
            loseStreak++;
            winStreak = 0;
            defeats.add(match);
        }
    }

    public Long latestGameId() {
        return latestMatch.getGameId();
    }

    public int latestVictories() {
        return (int) latestMatches.stream()
                .filter(SummonerMatch::isVictory)
                .count();
    }

    public double latestWinRate() {
        return (double) latestVictories() * PERCENT_MAX / (double) latestMatches.size();
    }

    public int victories() {
        return victories.size();
    }

    public int defeats() {
        return defeats.size();
    }

    public double absoluteWinRate() {
        return (double) victories.size() * PERCENT_MAX / ((double) (victories.size() + defeats.size()));
    }

    public int winStreak() {
        return winStreak;
    }

    public int loseStreak() {
        return loseStreak;
    }
}
