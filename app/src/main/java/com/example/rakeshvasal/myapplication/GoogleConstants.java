package com.example.rakeshvasal.myapplication;

/**
 * Created by Rakeshvasal on 10-Jan-18.
 */

public class GoogleConstants {
    public static String CLIENT_ID = "823153286753-t1uouilsb2a69t0nsajtqrdq2apgrqra.apps.googleusercontent.com";
    // Use your own client id

    public static String CLIENT_SECRET = "X-EJZvVWTFNwAPrldt29dWBR";
    // Use your own client secret

    public static String REDIRECT_URI = "https://www.google.com";
    public static String GRANT_TYPE = "authorization_code";
    public static String TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    public static String OAUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    public static String OAUTH_SCOPE = "https://www.googleapis.com/auth/contacts.readonly";

    public static final String CONTACTS_URL = "https://www.google.com/m8/feeds/contacts/default/full";
    public static final int MAX_NB_CONTACTS = 1000;
    public static final String APP = "MyApplication";
}
