package com.github.borisskert.aramalltimetable.matchreference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lol")
public class MatchReferenceResource {

    private final MatchReferenceService matchReferenceService;

    @Autowired
    public MatchReferenceResource(MatchReferenceService matchReferenceService) {
        this.matchReferenceService = matchReferenceService;
    }

    @GetMapping(value = "/matchreference", params = "summoner")
    public MatchReferences createStats(@RequestParam("summoner") String summonerName) {
        return matchReferenceService.getMatchReferences(summonerName);
    }
}
