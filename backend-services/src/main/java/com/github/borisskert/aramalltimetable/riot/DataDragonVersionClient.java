package com.github.borisskert.aramalltimetable.riot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DataDragonVersionClient {

    private final RestTemplate restTemplate;

    public DataDragonVersionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getLatestVersion() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(
                "https://ddragon.leagueoflegends.com/api/versions.json",
                String[].class
        );

        String[] versions = response.getBody();
        return versions[0];
    }
}
