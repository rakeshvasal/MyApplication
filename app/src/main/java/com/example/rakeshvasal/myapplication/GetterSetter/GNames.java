package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 10-Mar-18.
 */

public class GNames
{
    private String displayNameLastFirst;

    private String givenName;

    private String displayName;



    public String getDisplayNameLastFirst ()
    {
        return displayNameLastFirst;
    }

    public void setDisplayNameLastFirst (String displayNameLastFirst)
    {
        this.displayNameLastFirst = displayNameLastFirst;
    }

    public String getGivenName ()
    {
        return givenName;
    }

    public void setGivenName (String givenName)
    {
        this.givenName = givenName;
    }

    public String getDisplayName ()
    {
        return displayName;
    }

    public void setDisplayName (String displayName)
    {
        this.displayName = displayName;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [displayNameLastFirst = "+displayNameLastFirst+", givenName = "+givenName+", displayName = "+displayName+"]";
    }
}