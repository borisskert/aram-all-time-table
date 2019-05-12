package com.github.borisskert.aramalltimetable.matchreference;

import com.github.borisskert.aramalltimetable.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchReferenceStore {
    private static final String MATCHLIST_STORE_NAME = "matchreference";

    private final Store store;

    @Autowired
    public MatchReferenceStore(Store store) {
        this.store = store;
    }

    public Optional<MatchReferences> find(String id) {
        return store.find(MATCHLIST_STORE_NAME, id, MatchReferences.class);
    }

    public void create(String id, MatchReferences matchReferences) {
        store.create(MATCHLIST_STORE_NAME, id, matchReferences);
    }

    public void update(String id, MatchReferences matchReferences) {
        store.update(MATCHLIST_STORE_NAME, id, matchReferences);
    }
}
