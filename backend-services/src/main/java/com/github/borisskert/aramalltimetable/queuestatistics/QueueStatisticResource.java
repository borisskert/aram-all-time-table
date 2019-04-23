package com.github.borisskert.aramalltimetable.queuestatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lol")
public class QueueStatisticResource {

    private final QueueStatisticsService queueStatisticsService;

    @Autowired
    public QueueStatisticResource(QueueStatisticsService queueStatisticsService) {
        this.queueStatisticsService = queueStatisticsService;
    }

    @GetMapping(value = "/queuestatistics", params = "summoner")
    public QueueStatistics createStats(@RequestParam("summoner") String summonerName) {
        return queueStatisticsService.getQueueStatistics(summonerName);
    }
}
