package com.github.borisskert.aramalltimetable.summoner;

import com.github.borisskert.aramalltimetable.NotFoundException;
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

    public Summoner getSummoner(String name) throws NotFoundException {
        Optional<Summoner> maybeSummoner = store.findByName(name);

        if (maybeSummoner.isPresent()) {
            return maybeSummoner.get();
        } else {
            throw new NotFoundException("Cannot find summoner by name '" + name + "'");
        }
    }

    public Summoner getSummonerByAccountId(String accountId) throws NotFoundException {
        Optional<Summoner> maybeSummoner = store.findByAccountId(accountId);

        if (maybeSummoner.isPresent()) {
            return maybeSummoner.get();
        } else {
            throw new NotFoundException("Cannot find summoner by account-id '" + accountId + "'");
        }
    }

    public void updateSummoner(String summonerName) {
        Summoner loadedSummoner = client.getSummoner(summonerName);
        Optional<Summoner> maybeSummoner = store.findByAccountId(loadedSummoner.getAccountId());

        if (maybeSummoner.isPresent()) {
            store.update(loadedSummoner);
        } else {
            store.create(loadedSummoner);
        }
    }
}
