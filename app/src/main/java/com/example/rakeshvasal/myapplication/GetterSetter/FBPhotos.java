package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 06-Dec-17.
 */

public class FBPhotos {
    String postfromid, postfrom, postlink, pictureurl, id, albumcreatedate, albumid, albumname;

    public FBPhotos() {
    }

    public FBPhotos(String postfromid, String postfrom, String postlink, String pictureurl, String id, String albumcreatedate, String albumid, String albumname) {
        this.postfromid = postfromid;
        this.postfrom = postfrom;
        this.postlink = postlink;
        this.pictureurl = pictureurl;
        this.id = id;
        this.albumcreatedate = albumcreatedate;
        this.albumid = albumid;
        this.albumname = albumname;
    }

    public String getPostfromid() {
        return postfromid;
    }

    public void setPostfromid(String postfromid) {
        this.postfromid = postfromid;
    }

    public String getPostfrom() {
        return postfrom;
    }

    public void setPostfrom(String postfrom) {
        this.postfrom = postfrom;
    }

    public String getPostlink() {
        return postlink;
    }

    public void setPostlink(String postlink) {
        this.postlink = postlink;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumcreatedate() {
        return albumcreatedate;
    }

    public void setAlbumcreatedate(String albumcreatedate) {
        this.albumcreatedate = albumcreatedate;
    }

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }
}
