package com.github.borisskert.aramalltimetable.history;

import com.github.borisskert.aramalltimetable.match.MatchService;
import com.github.borisskert.aramalltimetable.match.SummonerMatches;
import com.github.borisskert.aramalltimetable.match.SummonerMatchesFactory;
import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    private final SummonerService summonerService;
    private final HistoryStore historyStore;
    private final MatchService matchService;
    private final SummonerMatchesFactory summonerMatchesFactory;

    @Autowired
    public HistoryService(
            SummonerService summonerService,
            HistoryStore historyStore,
            MatchService matchService,
            SummonerMatchesFactory summonerMatchesFactory
    ) {
        this.summonerService = summonerService;
        this.historyStore = historyStore;
        this.matchService = matchService;
        this.summonerMatchesFactory = summonerMatchesFactory;
    }

    public History getHistory(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        return getHistory(summoner);
    }

    public History getHistoryForAccountId(String accountId) {
        Summoner summoner = summonerService.getSummonerByAccountId(accountId);
        return getHistory(summoner);
    }

    private History getHistory(Summoner summoner) {
        Optional<History> maybeHistory = historyStore.find(summoner.getAccountId());

        if (maybeHistory.isPresent()) {
            return maybeHistory.get();
        }

        History createdHistory = createHistory(summoner);
        historyStore.create(createdHistory);

        return createdHistory;
    }

    public void updateHistory(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        updateHistory(summoner);
    }

    public void updateHistoryForAccountId(String accountId) {
        Summoner summoner = summonerService.getSummonerByAccountId(accountId);
        updateHistory(summoner);
    }

    private void updateHistory(Summoner summoner) {
        List<Match> matches = matchService.refreshMatches(summoner.getAccountId());

        Optional<History> maybeHistory = historyStore.find(summoner.getAccountId());
        History freshHistory = historyFromMatches(summoner, matches);

        if (maybeHistory.isPresent()) {
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
        SummonerMatches summonerMatches = summonerMatchesFactory.createForSummoner(summoner.getAccountId());

        for (Match match : matches) {
            summonerMatches.addMatch(match);
            History.HistoryEntry entry = toHistoryEntry(summonerMatches);

            history.addEntry(entry);
        }

        return history;
    }

    private History.HistoryEntry toHistoryEntry(SummonerMatches summonerMatches) {
        History.HistoryEntry entry = new History.HistoryEntry();

        entry.setGameId(summonerMatches.latestGameId());
        entry.setVictories(summonerMatches.victories());
        entry.setDefeats(summonerMatches.defeats());
        entry.setAbsoluteWinRate(summonerMatches.absoluteWinRate());
        entry.setFormWinRate(summonerMatches.latestWinRate());
        entry.setWinStreak(summonerMatches.winStreak());
        entry.setLoseStreak(summonerMatches.loseStreak());

        return entry;
    }
}
