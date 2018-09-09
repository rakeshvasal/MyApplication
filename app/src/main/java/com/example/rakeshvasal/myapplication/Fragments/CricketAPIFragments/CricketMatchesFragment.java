package com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.MatchesAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CricketMatchesFragment extends BaseFragment implements MatchesAdapter.OnShareClickedListener {

    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    String API_KEY = "";
    private MatchesAdapter adapter;
    List<CricketMatch> matcheslist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        // Inflate the activity_facebook for this fragment
        view = inflater.inflate(R.layout.fragment_contat_api, container, false);
        // Inflate the layout for this fragment
        API_KEY = getResources().getString(R.string.cricapikey);

        recyclerView = (RecyclerView)view.findViewById(R.id.match_recycler);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.CRIC_INFO_BASE_URL).create(ApiInterface.class);
        Call<Matches> matches = apiInterface.getMatches(API_KEY);

        Log.d("match", matches.toString());

        try {
            showProgressDialog();
            matches.enqueue(new Callback<Matches>() {
                @Override
                public void onResponse(Call<Matches> call, Response<Matches> response) {
                    closeProgressDialog();
                    matcheslist = response.body().getResults();
                    ListtoJson();
                    createlist();
                }

                @Override
                public void onFailure(Call<Matches> call, Throwable t) {
                    log("onFailure",t);
                    closeProgressDialog();
                }
            });
        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }

        return view;
    }

    private void ListtoJson() {

        String json = new Gson().toJson(matcheslist);
        Log.d("json", json);

    }

    @Override
    public void ShareClicked(int match_id) {
        Bundle arg = new Bundle();
        arg.putInt("MATCH_ID", match_id);
        MatchDetailsFragment matchdetails = new MatchDetailsFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        matchdetails.setArguments(arg);
        transaction.add(R.id.fragment_container, matchdetails);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void createlist() {

        adapter = new MatchesAdapter(getActivity(), matcheslist);
        adapter.setOnShareClickedListener(this);
        longToast("" + adapter.getItemCount());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.code:
                try {
                    //Utils.openSourceFile(Device_Info.this, "Device_Info", "java");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
