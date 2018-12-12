package com.example.rakeshvasal.myapplication;

import com.example.rakeshvasal.myapplication.Interface.ApiInterface;

public class NetworkCallRepositery {

    public static String CRIC_INFO_BASE_URL = "http://cricapi.com/api/";

    public void getAllMatches(String apikey) {
        ApiClient apiClient = ApiClient.getInstancewithUrl(CRIC_INFO_BASE_URL);
    }


    public interface OnNetworkCallsResponse<T> {
        void onSuccess(T response);

        void onFailure();
    }
}
