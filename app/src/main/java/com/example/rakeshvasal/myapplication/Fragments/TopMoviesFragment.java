package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Activity.PosterActivity;
import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.Custom_Adapters.MovieDBAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieDataSet;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieListObject;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopMoviesFragment extends BaseFragment implements MovieDBAdapter.OnShareClickedListener {

    String concat_url;
    TextView filter;
    EditText page, region;
    LinearLayout filterlayout;
    boolean isvisible = false;
    Button search;
    String pagenumber = "";
    private ApiInterface apiInterface;
    List<MovieDataSet> movieList;
    JSONArray jsonArray;


    public TopMoviesFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_top_movies, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.MOVIEDB_BASE_URL).create(ApiInterface.class);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        filter = (TextView) root.findViewById(R.id.filter);
        search = (Button) root.findViewById(R.id.search);
        page = (EditText) root.findViewById(R.id.page);
        region = (EditText) root.findViewById(R.id.region);
        filterlayout = (LinearLayout) root.findViewById(R.id.filter_layout);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        operations();
        return root;
    }

    private void operations() {

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                concat_url = "";

                // creates empty builder, capacity 16
                StringBuilder sb = new StringBuilder("/movie/top_rated");
                // adds 9 character string at beginning
                sb.append("?api_key=" + getResources().getString(R.string.MovieDB_API_KEY));
                sb.append("&language=en-US");
                if (!page.getText().toString().equalsIgnoreCase("")) {
                    sb.append("&page=" + page.getText().toString());
                }
                if (!region.getText().toString().equalsIgnoreCase("")) {
                    sb.append("&region=" + region.getText().toString());
                }

                //new getTopmovies(getActivity(), sb.toString()).execute();

                callUsingRetrofit();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isvisible) {
                    filterlayout.setVisibility(View.VISIBLE);
                    isvisible = true;
                    filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_edit, 0);
                } else {
                    filterlayout.setVisibility(View.GONE);
                    isvisible = false;
                    filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_black_24dp, 0);
                }
            }
        });


    }

    private void callUsingRetrofit(){
        try {

            Call<MovieListObject> movies = apiInterface.getMovies(getResources().getString(R.string.MovieDB_API_KEY), "en-US", page.getText().toString(), region.getText().toString());


            showProgressDialog();
            movies.enqueue(new Callback<MovieListObject>() {
                @Override
                public void onResponse(Call<MovieListObject> call, Response<MovieListObject> response) {
                    closeProgressDialog();
                    movieList = response.body().getMovieDataSets();
                    MovieDBAdapter adapter = new MovieDBAdapter(movieList, getActivity(), "mtoprated");
                    recyclerView.setAdapter(adapter);
                    adapter.setOnShareClickedListener(TopMoviesFragment.this);
                }

                @Override
                public void onFailure(Call<MovieListObject> call, Throwable t) {
                    closeProgressDialog();

                    Log.e("movieexcep", t.toString());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            closeProgressDialog();
            Log.e("movieerror", e.getMessage());
        }
    }

    @Override
    public void ShareClicked(String url) {
        Intent intent = new Intent(getActivity(), PosterActivity.class);
        intent.putExtra("type", "1");
        intent.putExtra("poster_url", url);
        startActivityForResult(intent, 1);
    }

    @Override
    public void getDetails(String id) {
        Bundle arg = new Bundle();
        arg.putString("movieid", id);
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        Fragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arg);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
