package com.example.demo.models;

public class MatchRecord {
    private String team1;
    private String team2;
    private Integer team1goals;
    private Integer team2goals;

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Integer getTeam1goals() {
        return team1goals;
    }

    public void setTeam1goals(Integer team1goals) {
        this.team1goals = team1goals;
    }

    public Integer getTeam2goals() {
        return team2goals;
    }

    public void setTeam2goals(Integer team2goals) {
        this.team2goals = team2goals;
    }
}
