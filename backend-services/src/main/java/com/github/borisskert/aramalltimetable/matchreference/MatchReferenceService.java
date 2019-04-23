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

    public MatchReferences getMatchReferences(String summonerName) {
        Summoner summoner = summonerService.getSummoner(summonerName);
        Optional<MatchReferences> maybeMatchList = matchReferenceStore.find(summoner.getAccountId());

        if(maybeMatchList.isPresent()) {
            return maybeMatchList.get();
        } else {
            List<MatchReference> matchReferences = loadMatchReferences(summoner.getAccountId());
            MatchReferences matchList = new MatchReferences();
            matchList.setMatches(matchReferences);

            matchReferenceStore.create(summoner.getAccountId(), matchList);

            return matchList;
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
