package com.github.borisskert.aramalltimetable.table;

import com.github.borisskert.aramalltimetable.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TableStore {
    private static final String TABLE_STORE_NAME = "table";

    private final Store store;

    @Autowired
    public TableStore(Store store) {
        this.store = store;
    }

    public Optional<Table> find(String accountId) {
        return store.find(TABLE_STORE_NAME, accountId, Table.class);
    }

    public void create(Table table) {
        store.create(TABLE_STORE_NAME, table.getAccountId(), table);
    }

    public void update(Table table) {
        store.update(TABLE_STORE_NAME, table.getAccountId(), table);
    }
}
