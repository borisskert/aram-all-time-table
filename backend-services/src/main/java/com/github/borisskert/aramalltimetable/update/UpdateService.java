package com.github.borisskert.aramalltimetable.update;

import com.github.borisskert.aramalltimetable.history.HistoryService;
import com.github.borisskert.aramalltimetable.match.MatchService;
import com.github.borisskert.aramalltimetable.matchreference.MatchReferenceService;
import com.github.borisskert.aramalltimetable.queuestatistics.QueueStatisticsService;
import com.github.borisskert.aramalltimetable.records.RecordsService;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    private final SummonerService summonerService;
    private final MatchReferenceService matchReferenceService;
    private final MatchService matchService;
    private final QueueStatisticsService queueStatisticsService;
    private final HistoryService historyService;
    private final RecordsService recordsService;

    @Autowired
    public UpdateService(
            SummonerService summonerService,
            MatchReferenceService matchReferenceService,
            MatchService matchService,
            QueueStatisticsService queueStatisticsService,
            HistoryService historyService,
            RecordsService recordsService
    ) {
        this.summonerService = summonerService;
        this.matchReferenceService = matchReferenceService;
        this.matchService = matchService;
        this.queueStatisticsService = queueStatisticsService;
        this.historyService = historyService;
        this.recordsService = recordsService;
    }

    public void update(String summonerName) {
        summonerService.updateSummoner(summonerName);

        Summoner summoner = summonerService.getSummoner(summonerName);
        String accountId = summoner.getAccountId();

        matchReferenceService.refreshMatchReferencesByAccountId(accountId);
        matchService.updateMatches(accountId);
        queueStatisticsService.refreshQueueStatisticsByAccountId(accountId);
        historyService.updateHistoryByAccountId(accountId);
        recordsService.updateRecordsByAccountId(accountId);
    }
}
