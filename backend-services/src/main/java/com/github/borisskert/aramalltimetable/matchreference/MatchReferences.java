package com.github.borisskert.aramalltimetable.matchreference;

import com.github.borisskert.aramalltimetable.riot.model.MatchReference;

import java.util.ArrayList;
import java.util.List;

public class MatchReferences {
    private List<MatchReference> matches = new ArrayList<>();

    public List<MatchReference> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchReference> matches) {
        this.matches = matches;
    }
}
