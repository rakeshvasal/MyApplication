package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rakeshvasal on 28-Oct-17.
 */

public class MatchDetails {

    @SerializedName("stat")
    private String stat;
    @SerializedName("score")
    private String score;
    @SerializedName("team1")
    private String team1;
    @SerializedName("team2")
    private String team2;
    @SerializedName("Level")
    @Expose
    private String level;

    private List<MatchDetails> matches;

    public List getMatches() {
        return matches;
    }

    public String getStat() {
        return stat;
    }

    public String getScore() {
        return score;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getLevel() {
        return level;
    }

    /**
     *
     * @param level
     * The Level
     */
    public void setLevel(String level) {
        this.level = level;
    }

}
