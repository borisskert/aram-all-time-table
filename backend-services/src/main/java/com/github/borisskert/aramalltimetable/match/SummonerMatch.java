package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;

import java.util.List;

public class SummonerMatch {

    private final Match match;
    private final Summoner summoner;

    public SummonerMatch(Match match, Summoner summoner) {
        this.match = match;
        this.summoner = summoner;
    }

    public boolean isVictory() {
        List<Match.ParticipantIdentity> identities = match.getParticipantIdentities();

        List<Match.Participant> participants = match.getParticipants();
        Match.ParticipantIdentity ownIdentity = identities.stream()
                .filter(i -> i.getPlayer().getAccountId().equals(summoner.getAccountId()))
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

        return ownTeam.getWin().equals("Win");
    }
}
