package com.github.borisskert.aramalltimetable.history;

import java.util.ArrayList;
import java.util.List;

public class History {

    private String accountId;
    private List<HistoryEntry> entries = new ArrayList<>();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<HistoryEntry> getEntries() {
        return entries;
    }

    public void addEntry(HistoryEntry entry) {
        entries.add(entry);
    }

    public static class HistoryEntry {
        private long gameId;
        private int victories;
        private int defeats;
        private double absoluteWinRate;
        private double formWinRate;
        private int winStreak;
        private int loseStreak;

        public long getGameId() {
            return gameId;
        }

        public void setGameId(long gameId) {
            this.gameId = gameId;
        }

        public int getVictories() {
            return victories;
        }

        public void setVictories(int victories) {
            this.victories = victories;
        }

        public int getDefeats() {
            return defeats;
        }

        public void setDefeats(int defeats) {
            this.defeats = defeats;
        }

        public double getAbsoluteWinRate() {
            return absoluteWinRate;
        }

        public void setAbsoluteWinRate(double absoluteWinRate) {
            this.absoluteWinRate = absoluteWinRate;
        }

        public double getFormWinRate() {
            return formWinRate;
        }

        public void setFormWinRate(double formWinRate) {
            this.formWinRate = formWinRate;
        }

        public int getWinStreak() {
            return winStreak;
        }

        public void setWinStreak(int winStreak) {
            this.winStreak = winStreak;
        }

        public int getLoseStreak() {
            return loseStreak;
        }

        public void setLoseStreak(int loseStreak) {
            this.loseStreak = loseStreak;
        }
    }
}
