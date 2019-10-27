package com.github.borisskert.aramalltimetable.matchreference;

import com.github.borisskert.aramalltimetable.riot.LeagueOfLegendsClient;
import com.github.borisskert.aramalltimetable.riot.model.MatchReference;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.summoner.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchReferenceService {

    private static final int MATCH_MAXIMUM_RANGE = 100;
    private static final int DEFAULT_MATCH_BEGIN_INDEX = 0;
    private static final int DEFAULT_MATCH_END_INDEX = DEFAULT_MATCH_BEGIN_INDEX + MATCH_MAXIMUM_RANGE;

    private final LeagueOfLegendsClient client;
    private final SummonerService summonerService;
    private final MatchReferenceStore matchReferenceStore;

    @Autowired
    public MatchReferenceService(LeagueOfLegendsClient client, SummonerService summonerService, MatchReferenceStore matchReferenceStore) {
        this.client = client;
        this.summonerService = summonerService;
        this.matchReferenceStore = matchReferenceStore;
    }

    public MatchReferences getMatchReferencesBySummonerName(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        return getMatchReferencesByAccountId(summoner.getAccountId());
    }

    public MatchReferences getMatchReferencesByAccountId(String accountId) {
        Optional<MatchReferences> maybeMatchList = matchReferenceStore.find(accountId);
        return maybeMatchList.orElseGet(MatchReferences::new);
    }

    public void refreshMatchReferencesByAccountId(String accountId) {
        Optional<MatchReferences> maybeMatchList = matchReferenceStore.find(accountId);
        List<MatchReference> matchReferences = loadMatchReferences(accountId);

        MatchReferences matchList;
        if (maybeMatchList.isPresent()) {
            matchList = maybeMatchList.get();
            matchList.setMatches(matchReferences);

            matchReferenceStore.update(accountId, matchList);
        } else {
            matchList = new MatchReferences();
            matchList.setMatches(matchReferences);

            matchReferenceStore.create(accountId, matchList);
        }
    }

    private List<MatchReference> loadMatchReferences(String summonerAccountId) {
        List<MatchReference> foundMatches = new ArrayList<>();
        com.github.borisskert.aramalltimetable.riot.model.MatchList matchList;

        int beginIndex = DEFAULT_MATCH_BEGIN_INDEX;
        int endIndex = DEFAULT_MATCH_END_INDEX;

        do {
            matchList = client.getMatchList(summonerAccountId, beginIndex, endIndex);
            foundMatches.addAll(matchList.getMatches());
            beginIndex = endIndex;
            endIndex = beginIndex + MATCH_MAXIMUM_RANGE;
        } while (matchList.getTotalGames() > matchList.getEndIndex());

        return foundMatches;
    }
}
