package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 10-Mar-18.
 */

public class PhoneNumbers
{
    private String formattedType;

    private String value;

    private String type;

    private String canonicalForm;



    public String getFormattedType ()
    {
        return formattedType;
    }

    public void setFormattedType (String formattedType)
    {
        this.formattedType = formattedType;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getCanonicalForm ()
    {
        return canonicalForm;
    }

    public void setCanonicalForm (String canonicalForm)
    {
        this.canonicalForm = canonicalForm;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [formattedType = "+formattedType+", value = "+value+", type = "+type+", canonicalForm = "+canonicalForm+"]";
    }
}


