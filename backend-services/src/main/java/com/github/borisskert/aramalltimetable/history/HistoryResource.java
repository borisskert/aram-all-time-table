package com.github.borisskert.aramalltimetable.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lol")
public class HistoryResource {

    private final HistoryService historyService;

    @Autowired
    public HistoryResource(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(value = "/history", params = "summoner")
    public History getHistory(@RequestParam("summoner") String summonerName) {
        return historyService.getHistory(summonerName);
    }

    @PostMapping(value = "/history", params = "summoner")
    public void updateHistory(@RequestParam("summoner") String summonerName) {
        historyService.updateHistory(summonerName);
    }
}
