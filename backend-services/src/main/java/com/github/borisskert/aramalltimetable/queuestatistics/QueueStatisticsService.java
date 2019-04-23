package com.github.borisskert.aramalltimetable.queuestatistics;

import com.github.borisskert.aramalltimetable.match.MatchService;
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

    @Autowired
    public QueueStatisticsService(SummonerService summonerService, MatchService matchService, QueueStatisticsStore queueStatisticsStore) {
        this.summonerService = summonerService;
        this.matchService = matchService;
        this.queueStatisticsStore = queueStatisticsStore;
    }

    public QueueStatistics getQueueStatistics(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<QueueStatistics> maybeQueueStatistics = queueStatisticsStore.find(summoner.getAccountId());

        if(maybeQueueStatistics.isPresent()) {
            return maybeQueueStatistics.get();
        } else {
            QueueStatistics queueStatistics = calculateQueueStatistics(summonerName);

            queueStatisticsStore.create(summoner.getAccountId(), queueStatistics);

            return queueStatistics;
        }
    }

    private QueueStatistics calculateQueueStatistics(String summonerName) {
        QueueStatistics queueStatistics = new QueueStatistics();
        List<Match> matches = matchService.getMatches(summonerName);

        int games = 0;
        int victories = 0;
        int defeats = 0;

        QueueStatistic queueStatistic = new QueueStatistic();
        for (Match match : matches) {
            List<Match.ParticipantIdentity> identities = match.getParticipantIdentities();
            List<Match.Participant> participants = match.getParticipants();

            Match.ParticipantIdentity ownIdentity = identities.stream()
                    .filter(i -> i.getPlayer().getSummonerName().equals(summonerName))
                    .findFirst()
                    .get();

            Match.Participant ownParticipant = participants.stream()
                    .filter(p -> p.getParticipantId().equals(ownIdentity.getParticipantId()))
                    .findFirst()
                    .get();

            Match.Team ownTeam = match.getTeams()
                    .stream()
                    .filter(t -> t.getTeamId().equals(ownParticipant.getTeamId()))
                    .findFirst()
                    .get();

            if(ownTeam.getWin().equals("Win")) {
                victories++;
            } else {
                defeats++;
            }

            games++;
        }

        queueStatistic.setGames(games);
        queueStatistic.setVictories(victories);
        queueStatistic.setDefeats(defeats);

        queueStatistics.getQueueStatistics().put(Queue.ARAM, queueStatistic);

        return queueStatistics;
    }
}
