package com.example.rakeshvasal.myapplication.GetterSetter;

import java.util.List;

/**
 * Created by Rakeshvasal on 10-Mar-18.
 */

public class GPeople
{
    private List<GPhotos> photos;

    private String etag;

    private List<GNames> names;

    private PhoneNumbers[] phoneNumbers;

    private String resourceName;

    public List<GPhotos> getPhotos ()
    {
        return photos;
    }

    public void setPhotos (List<GPhotos> photos)
    {
        this.photos = photos;
    }

    public String getEtag ()
    {
        return etag;
    }

    public void setEtag (String etag)
    {
        this.etag = etag;
    }

    public List<GNames> getNames ()
    {
        return names;
    }

    public void setNames (List<GNames> names)
    {
        this.names = names;
    }

    public PhoneNumbers[] getPhoneNumbers ()
    {
        return phoneNumbers;
    }

    public void setPhoneNumbers (PhoneNumbers[] phoneNumbers)
    {
        this.phoneNumbers = phoneNumbers;
    }

    public String getResourceName ()
    {
        return resourceName;
    }

    public void setResourceName (String resourceName)
    {
        this.resourceName = resourceName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [photos = "+photos+", etag = "+etag+", names = "+names+", phoneNumbers = "+phoneNumbers+", resourceName = "+resourceName+"]";
    }
}