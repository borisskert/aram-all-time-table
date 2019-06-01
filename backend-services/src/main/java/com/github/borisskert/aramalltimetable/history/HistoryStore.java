package com.github.borisskert.aramalltimetable.history;

import com.github.borisskert.aramalltimetable.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoryStore {
    private static final String TABLE_STORE_NAME = "history";

    private final Store store;

    @Autowired
    public HistoryStore(Store store) {
        this.store = store;
    }

    public Optional<History> find(String accountId) {
        return store.find(TABLE_STORE_NAME, accountId, History.class);
    }

    public void create(History history) {
        store.create(TABLE_STORE_NAME, history.getAccountId(), history);
    }

    public void update(History history) {
        store.update(TABLE_STORE_NAME, history.getAccountId(), history);
    }
}
