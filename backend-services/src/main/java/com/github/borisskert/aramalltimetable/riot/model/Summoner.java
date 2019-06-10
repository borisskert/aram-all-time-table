package com.github.borisskert.aramalltimetable.riot.model;

public class Summoner {
    private String name;
    private String puuid;
    private String accountId;
    private String id;
    private Integer profileIconId;
    private Long summonerLevel;

    public String getName() {
        return name;
    }

    public String getPuuid() {
        return puuid;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getId() {
        return id;
    }

    public Integer getProfileIconId() {
        return profileIconId;
    }

    public Long getSummonerLevel() {
        return summonerLevel;
    }
}
