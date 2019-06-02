package com.github.borisskert.aramalltimetable.match;

import com.github.borisskert.aramalltimetable.AramProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummonerMatchesFactory {

    private final AramProperties properties;

    @Autowired
    public SummonerMatchesFactory(AramProperties properties) {
        this.properties = properties;
    }

    public SummonerMatches createForSummoner(String accountId) {
        return new SummonerMatches(properties.getConsideredMatches(), accountId);
    }
}
