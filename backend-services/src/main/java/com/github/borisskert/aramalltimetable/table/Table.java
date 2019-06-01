package com.github.borisskert.aramalltimetable.table;

import com.github.borisskert.aramalltimetable.queuestatistics.QueueStatistic;
import com.github.borisskert.aramalltimetable.riot.model.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private Map<String, TableEntry> entries = new HashMap<>();
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Map<String, TableEntry> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, TableEntry> entries) {
        this.entries = entries;
    }

    public void putEntry(String accountId, TableEntry entry) {
        this.entries.put(accountId, entry);
    }

    public static class TableEntry {
        private String summonerName;
        private Map<Queue, QueueStatistic> queueStatistics = new HashMap<>();
        private List<Match> matches = new ArrayList<>();

        public String getSummonerName() {
            return summonerName;
        }

        public void setSummonerName(String summonerName) {
            this.summonerName = summonerName;
        }

        public Map<Queue, QueueStatistic> getQueueStatistics() {
            return queueStatistics;
        }

        public void setQueueStatistics(Map<Queue, QueueStatistic> queueStatistics) {
            this.queueStatistics = queueStatistics;
        }

        public List<Match> getMatches() {
            return matches;
        }

        public void setMatches(List<Match> matches) {
            this.matches = matches;
        }

        public void addMatch(Match match) {
            this.matches.add(match);
        }

        public static class Match {
            private Long gameId;
            private Boolean sameTeam;
            private Boolean victory;

            public Long getGameId() {
                return gameId;
            }

            public void setGameId(Long gameId) {
                this.gameId = gameId;
            }

            public Boolean getSameTeam() {
                return sameTeam;
            }

            public void setSameTeam(Boolean sameTeam) {
                this.sameTeam = sameTeam;
            }

            public Boolean getVictory() {
                return victory;
            }

            public void setVictory(Boolean victory) {
                this.victory = victory;
            }
        }
    }
}
