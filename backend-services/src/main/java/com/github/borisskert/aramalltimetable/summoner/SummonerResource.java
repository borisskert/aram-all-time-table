package com.github.borisskert.aramalltimetable.summoner;

import com.github.borisskert.aramalltimetable.riot.model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lol")
public class SummonerResource {

    private final SummonerService summonerService;

    @Autowired
    public SummonerResource(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    @GetMapping(value = "/summoner", params = "name")
    public Summoner getSummoner(@RequestParam("name") String summonerName) {
        return summonerService.getSummoner(summonerName);
    }
}
