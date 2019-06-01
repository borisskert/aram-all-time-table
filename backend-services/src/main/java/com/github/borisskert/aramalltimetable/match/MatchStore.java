package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.riot.model.Match;
import com.github.borisskert.aramalltimetable.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchStore {
    private static final String MATCH_STORE_NAME = "match";
    private final Store store;

    @Autowired
    public MatchStore(Store store) {
        this.store = store;
    }

    public Optional<Match> find(final String id) {
        return store.find(MATCH_STORE_NAME, id, Match.class);
    }

    public void create(final String id, final Match match) {
        store.create(MATCH_STORE_NAME, id, match);
    }

    public void update(final String id, final Match match) {
        store.update(MATCH_STORE_NAME, id, match);
    }
}
