package com.github.borisskert.aramalltimetable.queuestatistics;

import com.github.borisskert.aramalltimetable.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueStatisticsStore {
    private static final String QUEUESTATISTICS_STORE_NAME = "queuestatistics";

    private final Store store;

    @Autowired
    public QueueStatisticsStore(Store store) {
        this.store = store;
    }

    public Optional<QueueStatistics> find(String id) {
        return store.find(QUEUESTATISTICS_STORE_NAME, id, QueueStatistics.class);
    }

    public void create(String id, QueueStatistics queueStatistics) {
        store.create(QUEUESTATISTICS_STORE_NAME, id, queueStatistics);
    }

    public void update(String id, QueueStatistics queueStatistics) {
        store.update(QUEUESTATISTICS_STORE_NAME, id, queueStatistics);
    }
}
