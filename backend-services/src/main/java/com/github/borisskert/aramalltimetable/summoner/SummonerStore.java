package com.github.borisskert.aramalltimetable.summoner;

import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.store.Store;
import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerStore {

    public static final String SUMMONER_STORE_NAME = "summoner";
    private final Store store;

    @Autowired
    public SummonerStore(Store store) {
        this.store = store;
    }

    public Optional<Summoner> find(final String id) {
        return store.find(SUMMONER_STORE_NAME, id, Summoner.class);
    }

    public Optional<Summoner> findByName(final String summonerName) {
        return store.find(SUMMONER_STORE_NAME, Filters.eq("name", summonerName), Summoner.class);
    }

    public void create(Summoner summoner) {
        store.create(SUMMONER_STORE_NAME, summoner.getId(), summoner);
    }
}
