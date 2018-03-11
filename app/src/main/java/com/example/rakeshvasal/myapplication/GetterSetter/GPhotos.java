package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 10-Mar-18.
 */

public class GPhotos
{
    private String gdefault;

    private String url;



    public String getDefault ()
    {
        return gdefault;
    }

    public void setDefault (String gdefault)
    {
        this.gdefault = gdefault;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [default = "+gdefault+", url = "+url+"]";
    }
}