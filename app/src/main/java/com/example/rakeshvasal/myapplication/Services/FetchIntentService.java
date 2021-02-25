package com.example.rakeshvasal.myapplication.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.rakeshvasal.myapplication.Activity.MultiThreadingTestActiivity;
import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.Custom_Adapters.MovieDBAdapter;
import com.example.rakeshvasal.myapplication.Fragments.TopMoviesFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieDataSet;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieListObject;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchIntentService(String name) {
        super(name);
    }

    public FetchIntentService() {
        super("IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String message = intent.getStringExtra("message");
        intent.setAction(MultiThreadingTestActiivity.FILTER_ACTION_KEY);
        SystemClock.sleep(3000);
        String echoMessage = "IntentService after a pause of 3 seconds echoes " + message;
        getMovieDetails(intent);
        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage", echoMessage));
    }

    private void getMovieDetails(final Intent intent) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.MOVIEDB_BASE_URL).create(ApiInterface.class);
        Call<MovieListObject> movies = apiInterface.getMovies(getResources().getString(R.string.MovieDB_API_KEY), "en-US", "", "");
        movies.enqueue(new Callback<MovieListObject>() {
            @Override
            public void onResponse(Call<MovieListObject> call, Response<MovieListObject> response) {
                List<MovieDataSet> movieList = response.body().getMovieDataSets();
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastData", "" + movieList));
            }

            @Override
            public void onFailure(Call<MovieListObject> call, Throwable t) {
                Log.e("movieexcep", t.toString());
            }
        });
    }
}

