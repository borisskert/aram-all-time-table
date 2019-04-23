package com.github.borisskert.aramalltimetable.leagueoflegends;

import com.github.borisskert.aramalltimetable.riot.LeagueOfLegendsClient;
import com.github.borisskert.aramalltimetable.riot.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueOfLegendsService {

    private final LeagueOfLegendsClient client;

    @Autowired
    public LeagueOfLegendsService(LeagueOfLegendsClient client) {
        this.client = client;
    }

    public Summoner getSummoner(String name) {
        return client.getSummoner(name);
    }
}
