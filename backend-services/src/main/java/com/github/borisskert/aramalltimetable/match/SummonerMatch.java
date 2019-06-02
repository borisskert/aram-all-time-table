package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.riot.model.Match;

import java.util.List;

public class SummonerMatch {

    private final Match match;
    private final String summonerAccountId;

    public SummonerMatch(Match match, String summonerAccountId) {
        this.match = match;
        this.summonerAccountId = summonerAccountId;
    }

    public boolean isVictory() {
        List<Match.ParticipantIdentity> identities = match.getParticipantIdentities();

        List<Match.Participant> participants = match.getParticipants();
        Match.ParticipantIdentity ownIdentity = identities.stream()
                .filter(i -> i.getPlayer().getAccountId().equals(summonerAccountId))
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
