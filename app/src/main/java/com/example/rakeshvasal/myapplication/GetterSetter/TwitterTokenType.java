package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakeshvasal on 06-Jan-18.
 */
public class TwitterTokenType {

    @SerializedName("token_type")
    public String tokenType;

    @SerializedName("access_token")
    public String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public TwitterTokenType(String tokenType, String accessToken) {

        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }
}
