package com.github.borisskert.aramalltimetable.riot;

import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.riot.model.MatchList;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LeagueOfLegendsClient {
    private static final String ARAM_QUEUE_ID = "450";

    private final RiotProperties properties;
    private final RiotRestTemplate restTemplate;

    @Autowired
    public LeagueOfLegendsClient(RiotProperties properties, RiotRestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    /**
     * https://developer.riotgames.com/api-methods/#summoner-v4/GET_getBySummonerName
     */
    public Summoner getSummoner(String summonerName) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("summonerName", summonerName);
        uriVariables.put("apiKey", properties.getApiKey());

        ResponseEntity<Summoner> response = restTemplate.getForEntity(
                properties.getBaseUrl() + "/summoner/v4/summoners/by-name/{summonerName}?api_key={apiKey}",
                Summoner.class,
                uriVariables
        );

        return response.getBody();
    }

    /**
     * https://developer.riotgames.com/api-methods/#match-v4/GET_getMatchlist
     */
    public MatchList getMatchList(String encryptedAccountId, Integer beginIndex, Integer endIndex) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("accountId", encryptedAccountId);
        uriVariables.put("apiKey", properties.getApiKey());
        uriVariables.put("queue", ARAM_QUEUE_ID);
        uriVariables.put("beginIndex", beginIndex.toString());
        uriVariables.put("endIndex", endIndex.toString());

        ResponseEntity<MatchList> response = restTemplate.getForEntity(
                properties.getBaseUrl() + "/match/v4/matchlists/by-account/{accountId}" +
                        "?api_key={apiKey}" +
                        "&queue={queue}" +
                        "&beginIndex={beginIndex}" +
                        "&endIndex={endIndex}",
                MatchList.class,
                uriVariables
        );

        return response.getBody();
    }

    /**
     * https://developer.riotgames.com/api-methods/#match-v4/GET_getMatch
     */
    public Match getMatch(Long gameId) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("apiKey", properties.getApiKey());
        uriVariables.put("gameId", gameId.toString());

        ResponseEntity<Match> response = restTemplate.getForEntity(
                properties.getBaseUrl() + "/match/v4/matches/{gameId}" +
                        "?api_key={apiKey}",
                Match.class,
                uriVariables
        );

        return response.getBody();
    }
}
