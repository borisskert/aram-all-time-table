package com.github.borisskert.aramalltimetable.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lol")
public class TableResource {

    private final TableService tableService;

    @Autowired
    public TableResource(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping(value = "/table", params = "summoner")
    public Table createTable(@RequestParam("summoner") String summonerName) {
        return tableService.getTableBySummonerName(summonerName);
    }

    @PostMapping(value = "/table", params = "summoner")
    public void updateTable(@RequestParam("summoner") String summonerName) {
//        tableService.refreshTableBySummonerName(summonerName);
    }
}
