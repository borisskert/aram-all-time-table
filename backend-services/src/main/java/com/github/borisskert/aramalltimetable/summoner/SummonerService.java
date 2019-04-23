package com.github.borisskert.aramalltimetable.summoner;

import com.github.borisskert.aramalltimetable.riot.LeagueOfLegendsClient;
import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerService {

    private final LeagueOfLegendsClient client;
    private final SummonerStore store;

    @Autowired
    public SummonerService(LeagueOfLegendsClient client, SummonerStore store) {
        this.client = client;
        this.store = store;
    }

    public Summoner getSummoner(String name) {
        Optional<Summoner> maybeSummoner = store.findByName(name);

        if (maybeSummoner.isPresent()) {
            return maybeSummoner.get();
        } else {
            Summoner loadedSummoner = client.getSummoner(name);
            store.create(loadedSummoner);

            return loadedSummoner;
        }
    }
}
