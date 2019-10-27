package com.github.borisskert.aramalltimetable.history;

import com.github.borisskert.aramalltimetable.NotFoundException;
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
        return getHistoryForAccountId(summoner.getAccountId());
    }

    public History getHistoryForAccountId(String accountId) {
        Optional<History> maybeHistory = historyStore.find(accountId);

        if (maybeHistory.isPresent()) {
            return maybeHistory.get();
        } else {
            throw new NotFoundException("Cannot find history for account-id '" + accountId + "'");
        }
    }

    public void updateHistoryByAccountId(String accountId) {
        List<Match> matches = matchService.getMatchesByAccountId(accountId);

        Optional<History> maybeHistory = historyStore.find(accountId);
        History freshHistory = historyFromMatches(accountId, matches);

        if (maybeHistory.isPresent()) {
            historyStore.update(freshHistory);
        } else {
            historyStore.create(freshHistory);
        }
    }

    private History historyFromMatches(String accountId, List<Match> matches) {
        History history = new History();
        history.setAccountId(accountId);

        Collections.sort(matches);
        SummonerMatches summonerMatches = summonerMatchesFactory.createForSummoner(accountId);

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
