package com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.Custom_Adapters.MatchCalenderAdapter;
import com.example.rakeshvasal.myapplication.Fragments.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.CalenderMatch;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.DateFormattingClass;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchCalenderFragment extends BaseFragment {


    List<CalenderMatch> calenderMatches = new ArrayList<>();
    RecyclerView recyclerView;
    public MatchCalenderFragment() {
        // Required empty public constructor
    }

    private ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_match_calender, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Utils.API_KEY = getResources().getString(R.string.cricapikey);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.CRIC_INFO_BASE_URL).create(ApiInterface.class);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("apikey", Utils.API_KEY);

            Call<JsonObject> matchCalender = apiInterface.getMatchCalender(jsonObject);

            matchCalender.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        JsonObject match = response.body();
                        JSONObject jsonObject1 = new JSONObject(match.toString());
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            CalenderMatch obj = gson.fromJson("" + jsonObject2, CalenderMatch.class);
                            String date = obj.getDate();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                            Date date1 = formatter.parse(date);
                            String fromateddate = formatter.format(date1);
                            DateFormattingClass dateFormattingClass = new DateFormattingClass();
                            String date2 = dateFormattingClass.formatDate(getActivity(), fromateddate, "dd MMM yyyy", "dd/MM/YYYY");
                            obj.setDate(date2);
                            calenderMatches.add(obj);
                        }
                        MatchCalenderAdapter adapter = new MatchCalenderAdapter(getActivity(),calenderMatches);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //JSONObject jsonObject1 = match.getAsJsonObject(""+match);

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    logInfo("Calenderdatafail" + t);
                    shortToast("fail");
                }
            });
        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }


        return view;
    }

}
