package com.github.borisskert.aramalltimetable.riot.model;

import java.util.List;

public class Match implements Comparable<Match> {

    private Long gameId;
    private List<ParticipantIdentity> participantIdentities;
    private List<Team> teams;
    private List<Participant> participants;
    private Long gameCreation;

    public Long getGameId() {
        return gameId;
    }

    public List<ParticipantIdentity> getParticipantIdentities() {
        return participantIdentities;
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
            private String summonerName;
            private String summonerId;
            private String accountId;

            public String getSummonerName() {
                return summonerName;
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
        private Integer teamId;
        private String win;

        public Integer getTeamId() {
            return teamId;
        }

        public String getWin() {
            return win;
        }
    }

    public static class Participant {
        private Integer participantId;
        private Boolean win;
        private Integer teamId;

        public Integer getParticipantId() {
            return participantId;
        }

        public Boolean getWin() {
            return win;
        }

        public Integer getTeamId() {
            return teamId;
        }
    }
}
