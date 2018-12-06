package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 08-Oct-17.
 */

public class CardItems {

    private String title,subtitle;
    int thumbnail;

    public CardItems(int drawable,String title,String subtitle){
        this.title=title;
        this.subtitle=subtitle;
        this.thumbnail=drawable;
    }

    public int getThumbnail(){
        return thumbnail;
    }
    public String getTitle(){
        return title;
    }
    public String getSubtitle(){
        return subtitle;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setSubtitle(String subtitle){
        this.subtitle=subtitle;
    }
    public void setThumbnail(int thumbnail){
        this.thumbnail=thumbnail;
    }
}
