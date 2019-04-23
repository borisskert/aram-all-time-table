package com.github.borisskert.aramalltimetable.riot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class LeagueOfLegendsClient {

    private final RiotProperties properties;
    private final RestTemplate restTemplate;

    @Autowired
    public LeagueOfLegendsClient(RiotProperties properties, RestTemplate restTemplate) {
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

}
