package com.github.borisskert.aramalltimetable.history;

import com.github.borisskert.aramalltimetable.match.MatchService;
import com.github.borisskert.aramalltimetable.match.SummonerMatch;
import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HistoryService {

    private final SummonerService summonerService;
    private final HistoryStore historyStore;
    private final MatchService matchService;

    @Autowired
    public HistoryService(SummonerService summonerService, HistoryStore historyStore, MatchService matchService) {
        this.summonerService = summonerService;
        this.historyStore = historyStore;
        this.matchService = matchService;
    }

    public History getHistory(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<History> maybeHistory = historyStore.find(summoner.getAccountId());

        if(maybeHistory.isPresent()) {
            return maybeHistory.get();
        }

        History createdHistory = createHistory(summoner);
        historyStore.create(createdHistory);

        return createdHistory;
    }


    public void updateHistory(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        List<Match> matches = matchService.refreshMatches(summoner.getAccountId());

        Optional<History> maybeHistory = historyStore.find(summoner.getAccountId());
        History freshHistory = historyFromMatches(summoner, matches);

        if(maybeHistory.isPresent()) {
            historyStore.update(freshHistory);
        } else {
            historyStore.create(freshHistory);
        }
    }

    private History createHistory(Summoner summoner) {
        List<Match> matches = matchService.getMatchesByAccountId(summoner.getAccountId());
        return historyFromMatches(summoner, matches);
    }

    private History historyFromMatches(Summoner summoner, List<Match> matches) {
        History history = new History();
        history.setAccountId(summoner.getAccountId());

        Collections.sort(matches);
        LatestMatches latestMatches = new LatestMatches();

        for (Match match : matches) {
            SummonerMatch summonerMatch = new SummonerMatch(match, summoner);
            latestMatches.addMatch(summonerMatch);

            History.HistoryEntry entry = new History.HistoryEntry();
            entry.setGameId(match.getGameId());
            entry.setVictories(latestMatches.victories());
            entry.setDefeats(latestMatches.defeats());
            entry.setAbsoluteWinRate(latestMatches.absoluteWinRate());
            entry.setFormWinRate(latestMatches.latestWinRate());
            entry.setWinStreak(latestMatches.winStreak());
            entry.setLoseStreak(latestMatches.loseStreak());

            history.addEntry(entry);
        }

        return history;
    }

    private static class LatestMatches {
        private static final int MAX_CONSIDERED_MATCHES = 20;
        public static final double PERCENT_MAX = 100.0;

        private final LinkedList<SummonerMatch> latestTwentyMatches = new LinkedList<>();
        private final List<SummonerMatch> victories = new ArrayList<>();
        private final List<SummonerMatch> defeats = new ArrayList<>();
        private int winStreak;
        private int loseStreak;

        public void addMatch(SummonerMatch match) {
            latestTwentyMatches.add(match);

            if(match.isVictory()) {
                winStreak++;
                loseStreak = 0;
                victories.add(match);
            } else {
                loseStreak++;
                winStreak = 0;
                defeats.add(match);
            }

            if(latestTwentyMatches.size() > MAX_CONSIDERED_MATCHES) {
                latestTwentyMatches.removeFirst();
            }
        }

        public int latestVictories() {
            return (int) latestTwentyMatches.stream()
                    .filter(SummonerMatch::isVictory)
                    .count();
        }

        public double latestWinRate() {
            return (double)latestVictories() * PERCENT_MAX / (double)latestTwentyMatches.size();
        }

        public int victories() {
            return victories.size();
        }

        public int defeats() {
            return defeats.size();
        }

        public double absoluteWinRate() {
            return (double)victories.size() * PERCENT_MAX / ((double)(victories.size() + defeats.size()));
        }

        public int winStreak() {
            return winStreak;
        }

        public int loseStreak() {
            return loseStreak;
        }
    }
}
