package com.github.borisskert.aramalltimetable.table;

import com.github.borisskert.aramalltimetable.NotFoundException;
import com.github.borisskert.aramalltimetable.match.MatchService;
import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TableService {

    private final SummonerService summonerService;
    private final MatchService matchService;
    private final TableStore tableStore;

    @Autowired
    public TableService(
            SummonerService summonerService,
            MatchService matchService,
            TableStore tableStore
    ) {
        this.summonerService = summonerService;
        this.matchService = matchService;
        this.tableStore = tableStore;
    }

    public Table getTableBySummonerName(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        String accountId = summoner.getAccountId();
        Optional<Table> maybeTable = tableStore.find(accountId);

        if (maybeTable.isPresent()) {
            return maybeTable.get();
        } else {
            throw new NotFoundException("Cannot find table for summoner '" + summonerName + "'");
        }
    }

    public void updateTableByAccountId(String accountId) {
        Optional<Table> maybeTable = tableStore.find(accountId);

        Table table = buildTable(accountId);

        if (maybeTable.isPresent()) {
            tableStore.update(table);
        } else {
            tableStore.create(table);
        }
    }

    private Table buildTable(String accountId) {
        Table table;
        List<Match> matches = matchService.getMatchesByAccountId(accountId);

        table = new Table();
        table.setAccountId(accountId);

        Map<String, Table.TableEntry.Builder> entries = new HashMap<>();

        for (Match match : matches) {
            List<Match.ParticipantIdentity> identities = match.getParticipantIdentities();

            List<Match.Participant> participants = match.getParticipants();
            Match.ParticipantIdentity ownIdentity = identities.stream()
                    .filter(i -> i.getPlayer().getAccountId().equals(accountId))
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
                    .filter(i -> !i.getPlayer().getAccountId().equals(accountId))
                    .collect(Collectors.toList());

            for (Match.ParticipantIdentity identity : otherIdentities) {
                String participantAccountId = identity.getPlayer().getAccountId();

                Table.TableEntry.Builder entryBuilder = Optional.ofNullable(entries.get(participantAccountId))
                        .orElse(Table.TableEntry.builder());

                entryBuilder.summoner(identity.getPlayer().getSummonerName());

                Match.Participant participant = participants.stream()
                        .filter(p -> p.getParticipantId().equals(identity.getParticipantId()))
                        .findFirst().get();

                Match.ParticipantIdentity.Player player = identity.getPlayer();

                Table.TableEntry.Match entryMatch = new Table.TableEntry.Match();
                entryMatch.setGameId(match.getGameId());

                Match.Team team = match.getTeams()
                        .stream()
                        .filter(t -> t.getTeamId().equals(participant.getTeamId()))
                        .findFirst()
                        .get();

                entryMatch.setSameTeam(team.getTeamId().equals(ownTeam.getTeamId()));
                entryMatch.setVictory(ownTeam.getWin().equals("Win"));
                entryBuilder.addMatch(entryMatch);

                entries.put(participantAccountId, entryBuilder);
            }
        }

        List<Table.TableEntry> tableEntries = entries.values()
                .stream()
                .map(Table.TableEntry.Builder::build)
                .collect(Collectors.toList());

        Collections.sort(tableEntries);
        table.setEntries(tableEntries);

        return table;
    }
}
