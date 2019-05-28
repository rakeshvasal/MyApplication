package com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.Custom_Adapters.FindPlayerAdapter;
import com.example.rakeshvasal.myapplication.Fragments.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.Player;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFinderFragment extends BaseFragment implements FindPlayerAdapter.OnShareClickedListener {

    String API_KEY = "";
    private ApiInterface apiInterface;
    EditText et_search_text;
    Button btn_search;
    RecyclerView recyclerView;
    List<Player> playerList = new ArrayList<>();

    public PlayerFinderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_player_finder, container, false);
        et_search_text = (EditText) view.findViewById(R.id.searchtext);
        btn_search = (Button) view.findViewById(R.id.btn_search);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        API_KEY = getResources().getString(R.string.cricapikey);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.CRIC_INFO_BASE_URL).create(ApiInterface.class);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_search_text.getText().toString().equalsIgnoreCase("")) {
                    playerList.clear();
                    searchData(et_search_text.getText().toString());
                }
            }
        });


        return view;
    }

    private void searchData(String str_seatch_text) {
        Call<JsonObject> matches = apiInterface.getPlayerList(API_KEY, str_seatch_text);
        try {
            showProgressDialog();
            matches.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        closeProgressDialog();
                        JsonObject jsonObject = response.body();
                        Gson gson = new Gson();
                        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            Player obj = gson.fromJson("" + jsonObject2, Player.class);
                            playerList.add(obj);
                        }
                        if (playerList != null && playerList.size() != 0) {
                            FindPlayerAdapter adapter = new FindPlayerAdapter(playerList, getActivity());
                            adapter.setOnShareClickedListener(PlayerFinderFragment.this);
                            recyclerView.setAdapter(adapter);

                            adapter.notifyDataSetChanged();
                        }

                        logInfo(jsonObject1.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    log("onFailure", t);
                    closeProgressDialog();
                }
            });
        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }
    }

    @Override
    public void ShareClicked(String p_id) {
        Bundle bundle = new Bundle();
        bundle.putString("pid", p_id);
        PlayerDetailsFragment playerdetails = new PlayerDetailsFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        playerdetails.setArguments(bundle);
        transaction.replace(R.id.fragment_container, playerdetails);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
