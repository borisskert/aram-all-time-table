package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.matchreference.MatchReferenceService;
import com.github.borisskert.aramalltimetable.matchreference.MatchReferences;
import com.github.borisskert.aramalltimetable.riot.LeagueOfLegendsClient;
import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.MatchReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.borisskert.aramalltimetable.riot.LeagueOfLegendsClient.CURRENT_MATCH_API_VERSION;

@Service
public class MatchService {

    private final MatchReferenceService matchReferenceService;
    private final MatchStore matchStore;
    private final LeagueOfLegendsClient client;

    @Autowired
    public MatchService(
            MatchReferenceService matchReferenceService,
            MatchStore matchStore,
            LeagueOfLegendsClient client
    ) {
        this.matchReferenceService = matchReferenceService;
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
        String id = gameId.toString();
        Optional<Match> maybeMatch = matchStore.find(id);

        if (maybeMatch.isPresent()) {
            Match match = maybeMatch.get();
            String apiVersion = match.getApiVersion();

            if(!CURRENT_MATCH_API_VERSION.equalsIgnoreCase(apiVersion)) {
                Match loadedMatch = client.getMatch(gameId);
                matchStore.update(id, loadedMatch);
            }
        } else {
            Match loadedMatch = client.getMatch(gameId);
            matchStore.create(id, loadedMatch);
        }
    }

    public void updateMatches(String accountId) {
        MatchReferences matchReferences = matchReferenceService.getMatchReferencesByAccountId(accountId);

        matchReferences.getMatches()
                .stream()
                .map(MatchReference::getGameId)
                .forEach(this::updateMatch);
    }
}
