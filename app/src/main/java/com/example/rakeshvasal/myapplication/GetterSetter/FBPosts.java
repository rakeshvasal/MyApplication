package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Axisvation on 12/6/2017.
 */

public class FBPosts {

    String message,story,createdtime,id;

    public FBPosts(String message, String story, String createdtime, String id) {
        this.message = message;
        this.story = story;
        this.createdtime = createdtime;
        this.id = id;
    }

    public FBPosts() {
    }

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
