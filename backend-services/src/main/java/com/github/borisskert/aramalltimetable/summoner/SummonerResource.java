package com.github.borisskert.aramalltimetable.summoner;

import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import com.github.borisskert.aramalltimetable.update.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lol")
public class SummonerResource {

    private final SummonerService summonerService;
    private final UpdateService updateService;

    @Autowired
    public SummonerResource(SummonerService summonerService, UpdateService updateService) {
        this.summonerService = summonerService;
        this.updateService = updateService;
    }

    @GetMapping(value = "/summoner", params = "name")
    public Summoner getSummoner(@RequestParam("name") String summonerName) {
        return summonerService.getSummoner(summonerName);
    }

    @PostMapping(value = "/summoner", params = "name")
    public void updateSummoner(@RequestParam("name") String summonerName) {
        updateService.update(summonerName);
    }
}
