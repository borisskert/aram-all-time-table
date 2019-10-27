package com.github.borisskert.aramalltimetable.riot.model;

import java.util.List;
import java.util.Map;

import static com.github.borisskert.aramalltimetable.riot.LeagueOfLegendsClient.CURRENT_MATCH_API_VERSION;

public class Match implements Comparable<Match> {
    private Integer seasonId;
    private Integer queueId;
    private Long gameId;
    private List<ParticipantIdentity> participantIdentities;
    private String gameVersion;
    private String platformId;
    private String gameMode;
    private Integer mapId;
    private String gameType;
    private List<Team> teams;
    private List<Participant> participants;
    private Long gameCreation;
    private Long gameDuration;
    private String apiVersion = CURRENT_MATCH_API_VERSION;

    public Integer getSeasonId() {
        return seasonId;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public Long getGameId() {
        return gameId;
    }

    public List<ParticipantIdentity> getParticipantIdentities() {
        return participantIdentities;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public Integer getMapId() {
        return mapId;
    }

    public String getGameType() {
        return gameType;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public Long getGameCreation() {
        return gameCreation;
    }

    public Long getGameDuration() {
        return gameDuration;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public int compareTo(Match o) {
        return Long.compare(this.gameCreation, o.gameCreation);
    }

    public static class ParticipantIdentity {
        private Player player;
        private Integer participantId;

        public Player getPlayer() {
            return player;
        }

        public Integer getParticipantId() {
            return participantId;
        }

        public static class Player {
            private String currentPlatformId;
            private String summonerName;
            private String matchHistoryUri;
            private String platformId;
            private String currentAccountId;
            private Integer profileIcon;
            private String summonerId;
            private String accountId;

            public String getCurrentPlatformId() {
                return currentPlatformId;
            }

            public String getSummonerName() {
                return summonerName;
            }

            public String getMatchHistoryUri() {
                return matchHistoryUri;
            }

            public String getPlatformId() {
                return platformId;
            }

            public String getCurrentAccountId() {
                return currentAccountId;
            }

            public Integer getProfileIcon() {
                return profileIcon;
            }

            public String getSummonerId() {
                return summonerId;
            }

            public String getAccountId() {
                return accountId;
            }
        }
    }

    public static class Team {
        private Boolean firstDragon;
        private Boolean firstInhibitor;
        private List<TeamBan> bans;
        private Integer baronKills;
        private Boolean firstRiftHerald;
        private Boolean firstBaron;
        private Integer riftHeraldKills;
        private Boolean firstBlood;
        private Integer teamId;
        private Boolean firstTower;
        private Integer vilemawKills;
        private Integer inhibitorKills;
        private Integer towerKills;
        private Integer dominionVictoryScore;
        private String win;
        private Integer dragonKills;

        public Boolean getFirstDragon() {
            return firstDragon;
        }

        public Boolean getFirstInhibitor() {
            return firstInhibitor;
        }

        public List<TeamBan> getBans() {
            return bans;
        }

        public Integer getBaronKills() {
            return baronKills;
        }

        public Boolean getFirstRiftHerald() {
            return firstRiftHerald;
        }

        public Boolean getFirstBaron() {
            return firstBaron;
        }

        public Integer getRiftHeraldKills() {
            return riftHeraldKills;
        }

        public Boolean getFirstBlood() {
            return firstBlood;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public Boolean getFirstTower() {
            return firstTower;
        }

        public Integer getVilemawKills() {
            return vilemawKills;
        }

        public Integer getInhibitorKills() {
            return inhibitorKills;
        }

        public Integer getTowerKills() {
            return towerKills;
        }

        public Integer getDominionVictoryScore() {
            return dominionVictoryScore;
        }

        public String getWin() {
            return win;
        }

        public Integer getDragonKills() {
            return dragonKills;
        }

        public static class TeamBan {
            private Integer pickTurn;
            private Integer championId;

            public Integer getPickTurn() {
                return pickTurn;
            }

            public Integer getChampionId() {
                return championId;
            }
        }
    }

    public static class Participant {
        private ParticipantStats stats;
        private Integer participantId;
        private List<Rune> runes;
        private ParticipantTimeline timeline;
        private Integer teamId;
        private Integer spell2Id;
        private List<Mastery> masteries;
        private String highestAchievedSeasonTier;
        private Integer spell1Id;
        private Integer championId;

        public ParticipantStats getStats() {
            return stats;
        }

        public Integer getParticipantId() {
            return participantId;
        }

        public List<Rune> getRunes() {
            return runes;
        }

        public ParticipantTimeline getTimeline() {
            return timeline;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public Integer getSpell2Id() {
            return spell2Id;
        }

        public List<Mastery> getMasteries() {
            return masteries;
        }

        public String getHighestAchievedSeasonTier() {
            return highestAchievedSeasonTier;
        }

        public Integer getSpell1Id() {
            return spell1Id;
        }

        public Integer getChampionId() {
            return championId;
        }

        public final class ParticipantStats {
            private Boolean firstBloodAssist;
            private Long visionScore;
            private Long magicDamageDealtToChampions;
            private Long damageDealtToObjectives;
            private Integer totalTimeCrowdControlDealt;
            private Integer longestTimeSpentLiving;
            private Integer perk1Var1;
            private Integer perk1Var3;
            private Integer perk1Var2;
            private Integer tripleKills;
            private Integer perk3Var3;
            private Integer nodeNeutralizeAssist;
            private Integer perk3Var2;
            private Integer playerScore9;
            private Integer playerScore8;
            private Integer kills;
            private Integer playerScore1;
            private Integer playerScore0;
            private Integer playerScore3;
            private Integer playerScore2;
            private Integer playerScore5;
            private Integer playerScore4;
            private Integer playerScore7;
            private Integer playerScore6;
            private Integer perk5Var1;
            private Integer perk5Var3;
            private Integer perk5Var2;
            private Integer totalScoreRank;
            private Integer neutralMinionsKilled;
            private Long damageDealtToTurrets;
            private Long physicalDamageDealtToChampions;
            private Integer nodeCapture;
            private Integer largestMultiKill;
            private Integer perk2Var2;
            private Integer perk2Var3;
            private Integer totalUnitsHealed;
            private Integer perk2Var1;
            private Integer perk4Var1;
            private Integer perk4Var2;
            private Integer perk4Var3;
            private Integer wardsKilled;
            private Integer largestCriticalStrike;
            private Integer largestKillingSpree;
            private Integer quadraKills;
            private Integer teamObjective;
            private Long magicDamageDealt;
            private Integer item2;
            private Integer item3;
            private Integer item0;
            private Integer neutralMinionsKilledTeamJungle;
            private Integer item6;
            private Integer item4;
            private Integer item5;
            private Integer perk1;
            private Integer perk0;
            private Integer perk3;
            private Integer perk2;
            private Integer perk5;
            private Integer perk4;
            private Integer perk3Var1;
            private Long damageSelfMitigated;
            private Long magicalDamageTaken;
            private Boolean firstInhibitorKill;
            private Long trueDamageTaken;
            private Integer nodeNeutralize;
            private Integer assists;
            private Integer combatPlayerScore;
            private Integer perkPrimaryStyle;
            private Integer goldSpent;
            private Long trueDamageDealt;
            private Integer participantId;
            private Long totalDamageTaken;
            private Long physicalDamageDealt;
            private Integer sightWardsBoughtInGame;
            private Long totalDamageDealtToChampions;
            private Long physicalDamageTaken;
            private Integer totalPlayerScore;
            private Boolean win;
            private Integer objectivePlayerScore;
            private Long totalDamageDealt;
            private Integer item1;
            private Integer neutralMinionsKilledEnemyJungle;
            private Integer deaths;
            private Integer wardsPlaced;
            private Integer perkSubStyle;
            private Integer turretKills;
            private Boolean firstBloodKill;
            private Long trueDamageDealtToChampions;
            private Integer goldEarned;
            private Integer killingSprees;
            private Integer unrealKills;
            private Integer altarsCaptured;
            private Boolean firstTowerAssist;
            private Boolean firstTowerKill;
            private Integer champLevel;
            private Integer doubleKills;
            private Integer nodeCaptureAssist;
            private Integer inhibitorKills;
            private Boolean firstInhibitorAssist;
            private Integer perk0Var1;
            private Integer perk0Var2;
            private Integer perk0Var3;
            private Integer visionWardsBoughtInGame;
            private Integer altarsNeutralized;
            private Integer pentaKills;
            private Long totalHeal;
            private Integer totalMinionsKilled;
            private Long timeCCingOthers;

            public Boolean getFirstBloodAssist() {
                return firstBloodAssist;
            }

            public Long getVisionScore() {
                return visionScore;
            }

            public Long getMagicDamageDealtToChampions() {
                return magicDamageDealtToChampions;
            }

            public Long getDamageDealtToObjectives() {
                return damageDealtToObjectives;
            }

            public Integer getTotalTimeCrowdControlDealt() {
                return totalTimeCrowdControlDealt;
            }

            public Integer getLongestTimeSpentLiving() {
                return longestTimeSpentLiving;
            }

            public Integer getPerk1Var1() {
                return perk1Var1;
            }

            public Integer getPerk1Var3() {
                return perk1Var3;
            }

            public Integer getPerk1Var2() {
                return perk1Var2;
            }

            public Integer getTripleKills() {
                return tripleKills;
            }

            public Integer getPerk3Var3() {
                return perk3Var3;
            }

            public Integer getNodeNeutralizeAssist() {
                return nodeNeutralizeAssist;
            }

            public Integer getPerk3Var2() {
                return perk3Var2;
            }

            public Integer getPlayerScore9() {
                return playerScore9;
            }

            public Integer getPlayerScore8() {
                return playerScore8;
            }

            public Integer getKills() {
                return kills;
            }

            public Integer getPlayerScore1() {
                return playerScore1;
            }

            public Integer getPlayerScore0() {
                return playerScore0;
            }

            public Integer getPlayerScore3() {
                return playerScore3;
            }

            public Integer getPlayerScore2() {
                return playerScore2;
            }

            public Integer getPlayerScore5() {
                return playerScore5;
            }

            public Integer getPlayerScore4() {
                return playerScore4;
            }

            public Integer getPlayerScore7() {
                return playerScore7;
            }

            public Integer getPlayerScore6() {
                return playerScore6;
            }

            public Integer getPerk5Var1() {
                return perk5Var1;
            }

            public Integer getPerk5Var3() {
                return perk5Var3;
            }

            public Integer getPerk5Var2() {
                return perk5Var2;
            }

            public Integer getTotalScoreRank() {
                return totalScoreRank;
            }

            public Integer getNeutralMinionsKilled() {
                return neutralMinionsKilled;
            }

            public Long getDamageDealtToTurrets() {
                return damageDealtToTurrets;
            }

            public Long getPhysicalDamageDealtToChampions() {
                return physicalDamageDealtToChampions;
            }

            public Integer getNodeCapture() {
                return nodeCapture;
            }

            public Integer getLargestMultiKill() {
                return largestMultiKill;
            }

            public Integer getPerk2Var2() {
                return perk2Var2;
            }

            public Integer getPerk2Var3() {
                return perk2Var3;
            }

            public Integer getTotalUnitsHealed() {
                return totalUnitsHealed;
            }

            public Integer getPerk2Var1() {
                return perk2Var1;
            }

            public Integer getPerk4Var1() {
                return perk4Var1;
            }

            public Integer getPerk4Var2() {
                return perk4Var2;
            }

            public Integer getPerk4Var3() {
                return perk4Var3;
            }

            public Integer getWardsKilled() {
                return wardsKilled;
            }

            public Integer getLargestCriticalStrike() {
                return largestCriticalStrike;
            }

            public Integer getLargestKillingSpree() {
                return largestKillingSpree;
            }

            public Integer getQuadraKills() {
                return quadraKills;
            }

            public Integer getTeamObjective() {
                return teamObjective;
            }

            public Long getMagicDamageDealt() {
                return magicDamageDealt;
            }

            public Integer getItem2() {
                return item2;
            }

            public Integer getItem3() {
                return item3;
            }

            public Integer getItem0() {
                return item0;
            }

            public Integer getNeutralMinionsKilledTeamJungle() {
                return neutralMinionsKilledTeamJungle;
            }

            public Integer getItem6() {
                return item6;
            }

            public Integer getItem4() {
                return item4;
            }

            public Integer getItem5() {
                return item5;
            }

            public Integer getPerk1() {
                return perk1;
            }

            public Integer getPerk0() {
                return perk0;
            }

            public Integer getPerk3() {
                return perk3;
            }

            public Integer getPerk2() {
                return perk2;
            }

            public Integer getPerk5() {
                return perk5;
            }

            public Integer getPerk4() {
                return perk4;
            }

            public Integer getPerk3Var1() {
                return perk3Var1;
            }

            public Long getDamageSelfMitigated() {
                return damageSelfMitigated;
            }

            public Long getMagicalDamageTaken() {
                return magicalDamageTaken;
            }

            public Boolean getFirstInhibitorKill() {
                return firstInhibitorKill;
            }

            public Long getTrueDamageTaken() {
                return trueDamageTaken;
            }

            public Integer getNodeNeutralize() {
                return nodeNeutralize;
            }

            public Integer getAssists() {
                return assists;
            }

            public Integer getCombatPlayerScore() {
                return combatPlayerScore;
            }

            public Integer getPerkPrimaryStyle() {
                return perkPrimaryStyle;
            }

            public Integer getGoldSpent() {
                return goldSpent;
            }

            public Long getTrueDamageDealt() {
                return trueDamageDealt;
            }

            public Integer getParticipantId() {
                return participantId;
            }

            public Long getTotalDamageTaken() {
                return totalDamageTaken;
            }

            public Long getPhysicalDamageDealt() {
                return physicalDamageDealt;
            }

            public Integer getSightWardsBoughtInGame() {
                return sightWardsBoughtInGame;
            }

            public Long getTotalDamageDealtToChampions() {
                return totalDamageDealtToChampions;
            }

            public Long getPhysicalDamageTaken() {
                return physicalDamageTaken;
            }

            public Integer getTotalPlayerScore() {
                return totalPlayerScore;
            }

            public Boolean getWin() {
                return win;
            }

            public Integer getObjectivePlayerScore() {
                return objectivePlayerScore;
            }

            public Long getTotalDamageDealt() {
                return totalDamageDealt;
            }

            public Integer getItem1() {
                return item1;
            }

            public Integer getNeutralMinionsKilledEnemyJungle() {
                return neutralMinionsKilledEnemyJungle;
            }

            public Integer getDeaths() {
                return deaths;
            }

            public Integer getWardsPlaced() {
                return wardsPlaced;
            }

            public Integer getPerkSubStyle() {
                return perkSubStyle;
            }

            public Integer getTurretKills() {
                return turretKills;
            }

            public Boolean getFirstBloodKill() {
                return firstBloodKill;
            }

            public Long getTrueDamageDealtToChampions() {
                return trueDamageDealtToChampions;
            }

            public Integer getGoldEarned() {
                return goldEarned;
            }

            public Integer getKillingSprees() {
                return killingSprees;
            }

            public Integer getUnrealKills() {
                return unrealKills;
            }

            public Integer getAltarsCaptured() {
                return altarsCaptured;
            }

            public Boolean getFirstTowerAssist() {
                return firstTowerAssist;
            }

            public Boolean getFirstTowerKill() {
                return firstTowerKill;
            }

            public Integer getChampLevel() {
                return champLevel;
            }

            public Integer getDoubleKills() {
                return doubleKills;
            }

            public Integer getNodeCaptureAssist() {
                return nodeCaptureAssist;
            }

            public Integer getInhibitorKills() {
                return inhibitorKills;
            }

            public Boolean getFirstInhibitorAssist() {
                return firstInhibitorAssist;
            }

            public Integer getPerk0Var1() {
                return perk0Var1;
            }

            public Integer getPerk0Var2() {
                return perk0Var2;
            }

            public Integer getPerk0Var3() {
                return perk0Var3;
            }

            public Integer getVisionWardsBoughtInGame() {
                return visionWardsBoughtInGame;
            }

            public Integer getAltarsNeutralized() {
                return altarsNeutralized;
            }

            public Integer getPentaKills() {
                return pentaKills;
            }

            public Long getTotalHeal() {
                return totalHeal;
            }

            public Integer getTotalMinionsKilled() {
                return totalMinionsKilled;
            }

            public Long getTimeCCingOthers() {
                return timeCCingOthers;
            }
        }

        public final class Rune {
            private Integer runeId;
            private Integer rank;

            public Integer getRuneId() {
                return runeId;
            }

            public Integer getRank() {
                return rank;
            }
        }

        public final class ParticipantTimeline {
            private String lane;
            private Integer participantId;
            private Map<String, Double> csDiffPerMinDeltas;
            private Map<String, Double> goldPerMinDeltas;
            private Map<String, Double> xpDiffPerMinDeltas;
            private Map<String, Double> creepsPerMinDeltas;
            private Map<String, Double> xpPerMinDeltas;
            private String role;
            private Map<String, Double> damageTakenDiffPerMinDeltas;
            private Map<String, Double> damageTakenPerMinDeltas;

            public String getLane() {
                return lane;
            }

            public Integer getParticipantId() {
                return participantId;
            }

            public Map<String, Double> getCsDiffPerMinDeltas() {
                return csDiffPerMinDeltas;
            }

            public Map<String, Double> getGoldPerMinDeltas() {
                return goldPerMinDeltas;
            }

            public Map<String, Double> getXpDiffPerMinDeltas() {
                return xpDiffPerMinDeltas;
            }

            public Map<String, Double> getCreepsPerMinDeltas() {
                return creepsPerMinDeltas;
            }

            public Map<String, Double> getXpPerMinDeltas() {
                return xpPerMinDeltas;
            }

            public String getRole() {
                return role;
            }

            public Map<String, Double> getDamageTakenDiffPerMinDeltas() {
                return damageTakenDiffPerMinDeltas;
            }

            public Map<String, Double> getDamageTakenPerMinDeltas() {
                return damageTakenPerMinDeltas;
            }
        }

        public final class Mastery {
            private Integer masteryId;
            private Integer rank;

            public Integer getMasteryId() {
                return masteryId;
            }

            public Integer getRank() {
                return rank;
            }
        }
    }
}
