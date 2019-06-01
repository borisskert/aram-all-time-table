package com.github.borisskert.aramalltimetable.table;

import com.github.borisskert.aramalltimetable.match.MatchService;
import com.github.borisskert.aramalltimetable.queuestatistics.QueueStatistics;
import com.github.borisskert.aramalltimetable.queuestatistics.QueueStatisticsService;
import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableService {

    private final SummonerService summonerService;
    private final MatchService matchService;
    private final QueueStatisticsService queueStatisticsService;
    private final TableStore tableStore;

    @Autowired
    public TableService(
            SummonerService summonerService,
            MatchService matchService,
            QueueStatisticsService queueStatisticsService,
            TableStore tableStore
    ) {
        this.summonerService = summonerService;
        this.matchService = matchService;
        this.queueStatisticsService = queueStatisticsService;
        this.tableStore = tableStore;
    }

    public Table getTableBySummonerName(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<Table> maybeTable = tableStore.find(summoner.getAccountId());

        Table table;
        if(maybeTable.isPresent()) {
            return maybeTable.get();
        } else {
            List<Match> matches = matchService.getMatchesByAccountId(summoner.getAccountId());

            table = new Table();
            table.setAccountId(summoner.getAccountId());

            tableStore.create(table);

            for(Match match : matches) {
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

                List<Match.ParticipantIdentity> otherIdentities = identities.stream()
                        .filter(i -> !i.getPlayer().getSummonerName().equals(summonerName))
                        .collect(Collectors.toList());

                for (Match.ParticipantIdentity identity : otherIdentities) {
                    Table.TableEntry entry = new Table.TableEntry();
                    entry.setSummonerName(identity.getPlayer().getSummonerName());

                    Match.Participant participant = participants.stream()
                            .filter(p -> p.getParticipantId().equals(identity.getParticipantId()))
                            .findFirst().get();

                    Match.ParticipantIdentity.Player player = identity.getPlayer();
                    QueueStatistics queueStatisticsForPlayer = queueStatisticsService.getQueueStatisticsByAccountId(player.getAccountId());
                    entry.setQueueStatistics(queueStatisticsForPlayer.getQueueStatistics());

                    Table.TableEntry.Match entryMatch = new Table.TableEntry.Match();
                    entryMatch.setGameId(match.getGameId());

                    Match.Team team = match.getTeams().stream().filter(t -> t.getTeamId().equals(participant.getTeamId())).findFirst().get();
                    entryMatch.setSameTeam(team.getTeamId().equals(ownTeam.getTeamId()));
                    entryMatch.setVictory(ownTeam.getWin().equals("Win"));
                    entry.addMatch(entryMatch);

                    table.putEntry(player.getAccountId(), entry);

                    tableStore.update(table);
                }
            }
        }

        return table;
    }
}
