package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rakeshvasal on 28-Oct-17.
 */

public class  MatchDetails {

    @SerializedName("stat")
    private String stat;
    @SerializedName("score")
    private String score;
    @SerializedName("team-1")
    private String team1;
    @SerializedName("team-2")
    private String team2;
    @SerializedName("id")
    private String match_id;
    public MatchDetails() {
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

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

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}
