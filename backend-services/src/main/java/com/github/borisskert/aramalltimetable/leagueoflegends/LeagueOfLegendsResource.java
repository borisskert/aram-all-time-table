package com.github.borisskert.aramalltimetable.leagueoflegends;

import com.github.borisskert.aramalltimetable.riot.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lol")
public class LeagueOfLegendsResource {

    private final LeagueOfLegendsService leagueOfLegends;

    @Autowired
    public LeagueOfLegendsResource(LeagueOfLegendsService leagueOfLegends) {
        this.leagueOfLegends = leagueOfLegends;
    }

    @GetMapping(value = "/summoner", params = "name")
    public Summoner getSummoner(@RequestParam("name") String summonerName) {
        return leagueOfLegends.getSummoner(summonerName);
    }
}
