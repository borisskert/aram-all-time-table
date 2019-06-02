package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.matchreference.MatchReferenceService;
import com.github.borisskert.aramalltimetable.matchreference.MatchReferences;
import com.github.borisskert.aramalltimetable.riot.LeagueOfLegendsClient;
import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.MatchReference;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchReferenceService matchReferenceService;
    private final SummonerService summonerService;
    private final MatchStore matchStore;
    private final LeagueOfLegendsClient client;

    @Autowired
    public MatchService(
            MatchReferenceService matchReferenceService,
            SummonerService summonerService,
            MatchStore matchStore,
            LeagueOfLegendsClient client
    ) {
        this.matchReferenceService = matchReferenceService;
        this.summonerService = summonerService;
        this.matchStore = matchStore;
        this.client = client;
    }

    public List<Match> getMatches(String summonerName) {
        MatchReferences matchReferences = matchReferenceService.getMatchReferencesBySummonerName(summonerName);

        return matchReferences.getMatches()
                .stream()
                .map(m -> getMatch(m.getGameId()))
                .collect(Collectors.toList());
    }

    public List<Match> getMatchesByAccountId(String accountId) {
        MatchReferences matchReferences = matchReferenceService.getMatchReferencesByAccountId(accountId);

        return matchReferences.getMatches()
                .stream()
                .map(m -> getMatch(m.getGameId()))
                .collect(Collectors.toList());
    }

    public List<Match> refreshMatches(String accountId) {
        MatchReferences matchReferences = matchReferenceService.refreshMatchReferencesByAccountId(accountId);

        return matchReferences.getMatches()
                .stream()
                .map(m -> getMatch(m.getGameId()))
                .collect(Collectors.toList());
    }

    private Match getMatch(Long gameId) {
        Optional<Match> maybeMatch = matchStore.find(gameId.toString());

        if (maybeMatch.isPresent()) {
            return maybeMatch.get();
        } else {
            Match loadedMatch = client.getMatch(gameId);
            matchStore.create(loadedMatch.getGameId().toString(), loadedMatch);

            return loadedMatch;
        }
    }

    private void updateMatch(Long gameId) {
        Match loadedMatch = client.getMatch(gameId);

        String id = gameId.toString();
        Optional<Match> maybeMatch = matchStore.find(id);

        if(maybeMatch.isPresent()) {
            matchStore.update(id, loadedMatch);
        } else {
            matchStore.create(id, loadedMatch);
        }
    }

    public void updateMatches(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        MatchReferences matchReferences = matchReferenceService.refreshMatchReferencesByAccountId(summoner.getAccountId());

        matchReferences.getMatches()
                .stream()
                .map(MatchReference::getGameId)
                .forEach(this::updateMatch);
    }
}
