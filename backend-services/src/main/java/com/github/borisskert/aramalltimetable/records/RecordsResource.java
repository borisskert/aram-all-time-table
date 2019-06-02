package com.github.borisskert.aramalltimetable.records;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lol")
public class RecordsResource {

    private final RecordsService recordsService;

    @Autowired
    public RecordsResource(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @GetMapping(value = "/records", params = "summoner")
    public Records getRecords(@RequestParam("summoner") String summonerName) {
        return recordsService.getRecords(summonerName);
    }

    @PostMapping(value = "/records", params = "summoner")
    public void updateRecords(@RequestParam("summoner") String summonerName) {
        recordsService.updateRecords(summonerName);
    }
}
