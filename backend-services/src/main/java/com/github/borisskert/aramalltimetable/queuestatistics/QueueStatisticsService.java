package com.github.borisskert.aramalltimetable.queuestatistics;

import com.github.borisskert.aramalltimetable.AramProperties;
import com.github.borisskert.aramalltimetable.match.MatchService;
import com.github.borisskert.aramalltimetable.match.SummonerMatches;
import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.Queue;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueueStatisticsService {

    private final SummonerService summonerService;
    private final MatchService matchService;
    private final QueueStatisticsStore queueStatisticsStore;
    private final AramProperties properties;

    @Autowired
    public QueueStatisticsService(
            SummonerService summonerService,
            MatchService matchService,
            QueueStatisticsStore queueStatisticsStore,
            AramProperties properties
    ) {
        this.summonerService = summonerService;
        this.matchService = matchService;
        this.queueStatisticsStore = queueStatisticsStore;
        this.properties = properties;
    }

    public QueueStatistics getQueueStatisticsBySummonerName(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<QueueStatistics> maybeQueueStatistics = queueStatisticsStore.find(summoner.getAccountId());

        if (maybeQueueStatistics.isPresent()) {
            return maybeQueueStatistics.get();
        } else {
            List<Match> matches = matchService.getMatches(summoner.getAccountId());
            QueueStatistics queueStatistics = calculateQueueStatistics(matches, summoner.getAccountId());

            queueStatisticsStore.create(summoner.getAccountId(), queueStatistics);

            return queueStatistics;
        }
    }

    public void refreshQueueStatisticsBySummonerName(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<QueueStatistics> maybeQueueStatistics = queueStatisticsStore.find(summoner.getAccountId());
        List<Match> matches = matchService.refreshMatches(summoner.getAccountId());
        QueueStatistics queueStatistics = calculateQueueStatistics(matches, summoner.getAccountId());

        if (maybeQueueStatistics.isPresent()) {
            queueStatisticsStore.update(summoner.getAccountId(), queueStatistics);
        } else {
            queueStatisticsStore.create(summoner.getAccountId(), queueStatistics);
        }
    }

    public QueueStatistics getQueueStatisticsByAccountId(String accountId) {
        Optional<QueueStatistics> maybeQueueStatistics = queueStatisticsStore.find(accountId);

        if (maybeQueueStatistics.isPresent()) {
            return maybeQueueStatistics.get();
        } else {
            List<Match> matches = matchService.getMatchesByAccountId(accountId);
            QueueStatistics queueStatistics = calculateQueueStatistics(matches, accountId);

            queueStatisticsStore.create(accountId, queueStatistics);

            return queueStatistics;
        }
    }

    private QueueStatistics calculateQueueStatistics(List<Match> matches, String accountId) {
        QueueStatistics queueStatistics = new QueueStatistics();

        QueueStatistic queueStatistic = new QueueStatistic();
        SummonerMatches summonerMatches = new SummonerMatches(properties.getConsideredMatches(), accountId);

        for (Match match : matches) {
            summonerMatches.addMatch(match);
        }

        queueStatistic.setGames(summonerMatches.victories() + summonerMatches.defeats());
        queueStatistic.setVictories(summonerMatches.victories());
        queueStatistic.setDefeats(summonerMatches.defeats());

        queueStatistics.getQueueStatistics().put(Queue.ARAM, queueStatistic);

        return queueStatistics;
    }
}
