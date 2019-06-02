package com.github.borisskert.aramalltimetable.records;

public class Records {
    private String accountId;
    private int maxWinStreak;
    private int maxLoseStreak;
    private double maxWinRate;
    private double minWinRate;
    private double maxFormWinRate;
    private double minFormWinRate;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getMaxWinStreak() {
        return maxWinStreak;
    }

    public void setMaxWinStreak(int maxWinStreak) {
        this.maxWinStreak = maxWinStreak;
    }

    public int getMaxLoseStreak() {
        return maxLoseStreak;
    }

    public void setMaxLoseStreak(int maxLoseStreak) {
        this.maxLoseStreak = maxLoseStreak;
    }

    public double getMaxWinRate() {
        return maxWinRate;
    }

    public void setMaxWinRate(double maxWinRate) {
        this.maxWinRate = maxWinRate;
    }

    public double getMinWinRate() {
        return minWinRate;
    }

    public void setMinWinRate(double minWinRate) {
        this.minWinRate = minWinRate;
    }

    public double getMaxFormWinRate() {
        return maxFormWinRate;
    }

    public void setMaxFormWinRate(double maxFormWinRate) {
        this.maxFormWinRate = maxFormWinRate;
    }

    public double getMinFormWinRate() {
        return minFormWinRate;
    }

    public void setMinFormWinRate(double minFormWinRate) {
        this.minFormWinRate = minFormWinRate;
    }
}
