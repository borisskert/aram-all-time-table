package com.github.borisskert.aramalltimetable.table;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<TableEntry> entries = new ArrayList<>();
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<TableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TableEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(TableEntry entry) {
        this.entries.add(entry);
    }

    public static class TableEntry implements Comparable<TableEntry> {
        private String summonerName;
        private int games = 0;
        private int gamesAgainst = 0;
        private int gamesWith = 0;
        private int victoriesAgainst = 0;
        private int victoriesWith = 0;
        private int defeatsAgainst = 0;
        private int defeatsWith = 0;

        public String getSummonerName() {
            return summonerName;
        }

        public int getGames() {
            return games;
        }

        public long getGamesAgainst() {
            return gamesAgainst;
        }

        public long getGamesWith() {
            return gamesWith;
        }

        public long getVictoriesAgainst() {
            return victoriesAgainst;
        }

        public long getVictoriesWith() {
            return victoriesWith;
        }

        public long getDefeatsAgainst() {
            return defeatsAgainst;
        }

        public long getDefeatsWith() {
            return defeatsWith;
        }

        @Override
        public int compareTo(TableEntry o) {
            int games = Integer.compare(o.games, this.games);

            if (games == 0) {
                int gamesWith = Integer.compare(o.gamesWith, this.gamesWith);

                if (gamesWith == 0) {
                    int victoriesWith = Integer.compare(o.victoriesWith, this.victoriesWith);

                    if(victoriesWith == 0) {
                        return Integer.compare(o.victoriesAgainst, this.victoriesAgainst);
                    }

                    return victoriesWith;
                }

                return gamesWith;
            }

            return games;
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

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String summonerName;
            private List<Match> matches = new ArrayList<>();

            private Builder() {

            }

            public Builder summoner(String summonerName) {
                this.summonerName = summonerName;
                return this;
            }

            public Builder addMatch(Match match) {
                this.matches.add(match);
                return this;
            }

            public TableEntry build() {
                TableEntry entry = new TableEntry();
                entry.summonerName = summonerName;

                for (Match match : matches) {
                    if (match.getSameTeam()) {
                        entry.gamesWith++;

                        if (match.getVictory()) {
                            entry.victoriesWith++;
                        } else {
                            entry.defeatsWith++;
                        }
                    } else {
                        entry.gamesAgainst++;

                        if (match.getVictory()) {
                            entry.victoriesAgainst++;
                        } else {
                            entry.defeatsAgainst++;
                        }
                    }
                }

                entry.games = matches.size();

                return entry;
            }
        }
    }
}
