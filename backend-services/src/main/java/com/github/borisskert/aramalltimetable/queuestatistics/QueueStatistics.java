package com.github.borisskert.aramalltimetable.queuestatistics;

import com.github.borisskert.aramalltimetable.riot.model.Queue;

import java.util.HashMap;
import java.util.Map;

public class QueueStatistics {

    public Map<Queue, QueueStatistic> queueStatistics = new HashMap<>();

    public Map<Queue, QueueStatistic> getQueueStatistics() {
        return queueStatistics;
    }
}
