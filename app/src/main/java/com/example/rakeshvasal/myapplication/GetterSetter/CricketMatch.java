package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakeshvasal on 26-Oct-17.
 */

public class CricketMatch {


    @SerializedName("team-1")
    private String team1;
    @SerializedName("team-2")
    private String team2;
    @SerializedName("type")
    private String type;
    @SerializedName("winner_team")
    private String winnerteam;
    @SerializedName("matchStarted")
    private String matchstarted;
    @SerializedName("date")
    private String date;
    @SerializedName("unique_id")
    private int uniqueId;

    public CricketMatch(String team1, String team2, String type, String matchstarted, String date, String winnerteam, int uniqueId){
        this.team1 = team1;
        this.team2 = team2;
        this.type = type;
        this.matchstarted = matchstarted;
        this.date = date;
        this.winnerteam = winnerteam;
        this.uniqueId = uniqueId;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWinnerteam(String winnerteam) {
        this.winnerteam = winnerteam;
    }

    public void setMatchstarted(String matchstarted) {
        this.matchstarted = matchstarted;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTeam1() {

        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getType() {
        return type;
    }

    public String getWinnerteam() {
        return winnerteam;
    }

    public String getMatchstarted() {
        return matchstarted;
    }

    public String getDate() {
        return date;
    }

    public int getUniqueId() {
        return uniqueId;
    }
}
