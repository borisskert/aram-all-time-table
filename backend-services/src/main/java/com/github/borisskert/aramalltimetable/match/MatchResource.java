package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.riot.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lol")
public class MatchResource {

    private final MatchService matchService;

    @Autowired
    public MatchResource(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(value = "/match", params = "summoner")
    public List<Match> getMatches(@RequestParam("summoner") String summonerName) {
        return matchService.getMatches(summonerName);
    }
}
