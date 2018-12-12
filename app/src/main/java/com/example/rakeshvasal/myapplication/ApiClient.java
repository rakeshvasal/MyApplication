package com.example.rakeshvasal.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rakeshvasal on 26-Oct-17.
 */

public class ApiClient {

    public static String CRIC_INFO_BASE_URL = "http://cricapi.com/api/";

    public static Retrofit retrofit = null;


    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(CRIC_INFO_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getInstancewithUrl(String url) {
        if (retrofit != null) {
            retrofit = null;
            retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;

    }

    public static Retrofit changeBaseURL(String newBaseURL) {
        {
            if (retrofit != null) {
                retrofit = null;
                retrofit = new Retrofit.Builder().baseUrl(newBaseURL).addConverterFactory(GsonConverterFactory.create()).build();
            }
            return retrofit;
        }
    }

    /*private static Retrofit instance;
    //this for instantiating the interceptor
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static OkHttpClient.Builder lognInHttpClient = new OkHttpClient.Builder();
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static final String API_CLIENT_ID = "testclientid";
    private static final String API_SECRET_KEY = "testsecretkey";
    private static final byte[] encodeClientIDandSecretKey = (API_CLIENT_ID + ":" + API_SECRET_KEY).getBytes();

    public static void setInstance(Retrofit instance) {
        Retrofitconfig.instance = instance;
    }

    //creating a builder for retrofit and adding a json converter to it
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create());


    // provides an instance fo retrofit
    public static Retrofit getRetrofit() {
        return builder.build();
    }

    // get the singleton instance of retrofit with the required headers
    public static Retrofit getRetrofitWithParams() {
        if (instance != null) {
            return instance;
        } else {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    OAuthResponse response = ApplicationPreferencesHelper.loadFromSharedPreferences(OAuthResponse.class, "oauth");
                    if (response != null && response.token_type != null && response.token_type.length() > 0) {
                        if (response.expires != null && response.expires.length() > 0) {
                            if (response.isOauthExpired()) {
                                Log.d("retorfit", "token expired");
                                if (response.refresh_token != null && response.refresh_token.length() > 0) {
                                    loginUsingRefreshToken(response.refresh_token);
                                    response = response.load();
                                }
                            }
                        }

                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", response.token_type + " " + response.access_token)
                                .header("Content-Type", "application/json")
                                .method(original.method(), original.body());
                        request = requestBuilder.build();
                    }
                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            instance = builder.client(client).build();
            return instance;
        }
    }

    *//**
     * Call this method if no headers are required as a part of the request
     *//*
    public static <S> S getDefaultInstance(Class<S> serviceClass) {
        if (API_SECRET_KEY.trim().length() > 0 && API_CLIENT_ID.trim().length() > 0) {
            lognInHttpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Basic " + Base64.encodeToString(encodeClientIDandSecretKey, Base64.NO_WRAP))
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        Retrofit retrofit = builder.client(lognInHttpClient.build()).build();
        return retrofit.create(serviceClass);
    }


    *//**
     * Call this method if any headers are required as a part of request
     * For adding any additional headers just add the .header in the below method, it takes string parameters for key and value
     *//*

    public static <S> S getInstanceWithHeaders(Class<S> serviceClass) {
        return getRetrofitWithParams().create(serviceClass);
    }

    public static <S> S getInstanceMultipart(Class<S> serviceClass) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = null;
                OAuthResponse response = ApplicationPreferencesHelper.loadFromSharedPreferences(OAuthResponse.class, "oauth");
                if (response != null && response.token_type != null && response.token_type.length() > 0) {
                    if (response.expires != null && response.expires.length() > 0) {
                        if (response.isOauthExpired()) {
                            if (response.refresh_token != null && response.refresh_token.length() > 0) {
                                loginUsingRefreshToken(response.refresh_token);
                                response = response.load();
                            }
                        }
                    }
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", response.token_type + " " + response.access_token)
                            .method(original.method(), original.body());
                    request = requestBuilder.build();
                }
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


    *//**
     * These are the helper methods to get the description and the multipart/form-data for retrofit hit
     *//*
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    public static MultipartBody.Part prepareFilePart(String partName, File file, String mimeType) {
        // use the FileUtils to get the actual file by uri
        // create RequestBody instance from file
        RequestBody requestFile = null;
        if (file != null) {
            requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        }
        if (requestFile != null) {
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        }
        return null;
        // MultipartBody.Part is used to send also the actual file name
    }

    public static void cancelAllRetrofitRequests() {
        okHttpClient.dispatcher().cancelAll();
    }

    private static void loginUsingRefreshToken(String refreshToken) throws SocketTimeoutException {
        CentralApi.getInstance().loginUsingRefresh(refreshToken);
    }
}*/

}
