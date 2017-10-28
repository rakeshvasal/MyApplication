package com.example.rakeshvasal.myapplication.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Custom_Adapters.GalleryAdapter;
import com.example.rakeshvasal.myapplication.Custom_Adapters.MatchesAdapter;
import com.example.rakeshvasal.myapplication.Fragments.MatchDetailsFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CricInfoAPI extends BaseActivity implements MatchesAdapter.OnShareClickedListener {

    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    String API_KEY = "";
    private MatchesAdapter adapter;
    List<CricketMatch> movies;
    FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contat_api);
        // Inflate the layout for this fragment
        API_KEY = getResources().getString(R.string.cricapikey);
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY", Toast.LENGTH_LONG).show();
            return;
        }
        recyclerView = (RecyclerView) findViewById(R.id.match_recycler);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        fm = getFragmentManager();
        Call<Matches> matches = apiInterface.getMatches(API_KEY);

        Log.d("match", matches.toString());

        try {
            matches.enqueue(new Callback<Matches>() {
                @Override
                public void onResponse(Call<Matches> call, Response<Matches> response) {
                    movies = response.body().getResults();
                    //matcheslist = response.body();
                    ListtoJson();
                    createlist();

                }

                @Override
                public void onFailure(Call<Matches> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ListtoJson() {

        String json = new Gson().toJson(movies);
        Log.d("json", json);

    }

    @Override
    public void ShareClicked(int match_id) {
        Bundle arg = new Bundle();
        arg.putLong("MATCH_ID", match_id);
        MatchDetailsFragment matchdetails = new MatchDetailsFragment();
        FragmentTransaction transaction = fm.beginTransaction();
        matchdetails.setArguments(arg);
        transaction.add(R.id.fragment_container, matchdetails);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void createlist() {

        adapter = new MatchesAdapter(CricInfoAPI.this, movies, fm);
        adapter.setOnShareClickedListener(this);
        longToast("" + adapter.getItemCount());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
