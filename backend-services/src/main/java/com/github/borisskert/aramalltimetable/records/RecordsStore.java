package com.github.borisskert.aramalltimetable.records;

import com.github.borisskert.aramalltimetable.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecordsStore {

    private static final String TABLE_STORE_NAME = "records";
    private final Store store;

    @Autowired
    public RecordsStore(Store store) {
        this.store = store;
    }

    public Optional<Records> find(String accountId) {
        return store.find(TABLE_STORE_NAME, accountId, Records.class);
    }

    public void create(Records history) {
        store.create(TABLE_STORE_NAME, history.getAccountId(), history);
    }

    public void update(Records history) {
        store.update(TABLE_STORE_NAME, history.getAccountId(), history);
    }
}
