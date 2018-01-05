package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Axisvation on 12/6/2017.
 */

public class FBFeeds {
    @SerializedName("message")
    String message;
    @SerializedName("story")
    String story;
    @SerializedName("created_time")
    String createdtime;
    @SerializedName("id")
    String id;
    @SerializedName("story_tags")
    String story_tags;
    @SerializedName("full_picture")
    String full_picture;
    @SerializedName("likes")
    String likes;

    public FBFeeds() {
    }

    public FBFeeds(String message, String story, String createdtime, String id) {
        this.message = message;
        this.story = story;
        this.createdtime = createdtime;
        this.id = id;
    }

    public FBFeeds(String message, String story, String createdtime, String id, String story_tags, String full_picture, String likes, String reactions) {
        this.message = message;
        this.story = story;
        this.createdtime = createdtime;
        this.id = id;
        this.story_tags = story_tags;
        this.full_picture = full_picture;
        this.likes = likes;
        this.reactions = reactions;
    }

    public String getStory_tags() {
        return story_tags;
    }

    public void setStory_tags(String story_tags) {
        this.story_tags = story_tags;
    }

    public String getFull_picture() {
        return full_picture;
    }

    public void setFull_picture(String full_picture) {
        this.full_picture = full_picture;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getReactions() {
        return reactions;
    }

    public void setReactions(String reactions) {
        this.reactions = reactions;
    }

    @SerializedName("reactions")
    String reactions;





    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
