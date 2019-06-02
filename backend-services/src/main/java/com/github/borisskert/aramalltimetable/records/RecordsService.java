package com.github.borisskert.aramalltimetable.records;

import com.github.borisskert.aramalltimetable.AramProperties;
import com.github.borisskert.aramalltimetable.history.History;
import com.github.borisskert.aramalltimetable.history.HistoryService;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecordsService {

    private final AramProperties properties;

    private final SummonerService summonerService;
    private final HistoryService historyService;
    private final RecordsStore recordsStore;

    @Autowired
    public RecordsService(
            AramProperties properties,
            SummonerService summonerService,
            HistoryService historyService,
            RecordsStore recordsStore
    ) {
        this.properties = properties;
        this.summonerService = summonerService;
        this.historyService = historyService;
        this.recordsStore = recordsStore;
    }

    public Records getRecords(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<Records> maybeRecords = recordsStore.find(summoner.getAccountId());

        if(maybeRecords.isPresent()) {
            return maybeRecords.get();
        }

        History history = historyService.getHistoryForAccountId(summoner.getAccountId());
        Records records = createRecords(history);

        recordsStore.create(records);

        return records;
    }

    public void updateRecords(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<Records> maybeRecords = recordsStore.find(summoner.getAccountId());

        historyService.updateHistoryForAccountId(summoner.getAccountId());
        History history = historyService.getHistoryForAccountId(summoner.getAccountId());
        Records records = createRecords(history);

        if(maybeRecords.isPresent()) {
            recordsStore.update(records);
        } else {
            recordsStore.create(records);
        }
    }

    private Records createRecords(History history) {
        Records records = new Records();
        records.setAccountId(history.getAccountId());

        int maxWinStreak = 0;
        int maxLoseStreak = 0;

        int games = 0;
        double maxWinRate = Double.MIN_VALUE;
        double minWinRate = Double.MAX_VALUE;

        double maxFormWinRate = Double.MIN_VALUE;
        double minFormWinRate = Double.MAX_VALUE;

        for (History.HistoryEntry entry : history.getEntries()) {
            games++;
            maxWinStreak = Math.max(maxWinStreak, entry.getWinStreak());
            maxLoseStreak = Math.max(maxLoseStreak, entry.getLoseStreak());

            if(games > properties.getConsideredMatches()) {
                maxWinRate = Math.max(maxWinRate, entry.getAbsoluteWinRate());
                minWinRate = Math.min(minWinRate, entry.getAbsoluteWinRate());
                maxFormWinRate = Math.max(maxFormWinRate, entry.getFormWinRate());
                minFormWinRate = Math.min(minFormWinRate, entry.getFormWinRate());
            }
        }

        records.setMaxWinStreak(maxWinStreak);
        records.setMaxLoseStreak(maxLoseStreak);
        records.setMaxWinRate(maxWinRate);
        records.setMinWinRate(minWinRate);
        records.setMaxFormWinRate(maxFormWinRate);
        records.setMinFormWinRate(minFormWinRate);

        return records;
    }
}
