package com.github.borisskert.aramalltimetable.summoner;

import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.store.Store;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerStore {
    private static final String SUMMONER_STORE_NAME = "summoner";

    private final Store store;

    @Autowired
    public SummonerStore(Store store) {
        this.store = store;
    }

    public Optional<Summoner> find(final String id) {
        return store.find(SUMMONER_STORE_NAME, id, Summoner.class);
    }

    public Optional<Summoner> findByName(final String summonerName) {
        return store.find(
                SUMMONER_STORE_NAME,
                Filters.regex("name", "^" + summonerName + " *$", "ig"),
                Summoner.class
        );
    }

    public Optional<Summoner> findByAccountId(final String accountId) {
        return store.find(SUMMONER_STORE_NAME, Filters.eq("accountId", accountId), Summoner.class);
    }

    public void create(Summoner summoner) {
        store.create(SUMMONER_STORE_NAME, summoner.getId(), summoner);
    }

    public void update(Summoner summoner) {
        store.update(SUMMONER_STORE_NAME, summoner.getId(), summoner);
    }
}
