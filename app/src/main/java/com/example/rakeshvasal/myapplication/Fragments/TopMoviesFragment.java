package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Activity.PosterActivity;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.MovieDBAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieDataSet;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.example.rakeshvasal.myapplication.Utilities.makeServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TopMoviesFragment extends BaseFragment implements MovieDBAdapter.OnShareClickedListener{

    String concat_url;
    TextView filter;
    EditText page, region;
    LinearLayout filterlayout;
    boolean isvisible = false;
    Button search;
    String pagenumber = "";

    public TopMoviesFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_top_movies, container, false);

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
                new getTopmovies(getActivity(), sb.toString()).execute();
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
                    filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.quantum_ic_keyboard_arrow_down_white_36, 0);
                }
            }
        });


    }

    @Override
    public void ShareClicked(String url) {
        Intent intent = new Intent(getActivity(), PosterActivity.class);
        intent.putExtra("type","1");
        intent.putExtra("poster_url",url);
        startActivityForResult(intent,1);
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


    public class getTopmovies extends AsyncTask<String, String, String> {

        String data, url, concat_url;
        ProgressDialog progressDialog;
        Context context;

        public getTopmovies(Context context, String concaturl) {
            this.context = context;
            this.concat_url = concaturl;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            Log.d("getTopMoviescancelled", s);
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            url = Utils.MOVIEDB_BASE_URL + concat_url;
            try {
                data = new makeServiceCall().makeServiceCall(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //setRecyclerViewAdapter(data);
            JSONObject datajson;
            ArrayList<MovieDataSet> datasetarray;

            if (data != null) {
                if (!data.equalsIgnoreCase("")) {
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if (jsonObject.has("page")) {
                            pagenumber = jsonObject.getString("page");
                        }
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        datasetarray = new ArrayList<MovieDataSet>(jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            datajson = jsonArray.getJSONObject(i);
                            MovieDataSet dataSet = fromJson(datajson);
                            datasetarray.add(dataSet);
                        }
                        MovieDBAdapter adapter = new MovieDBAdapter(datasetarray, getActivity(), "mtoprated");
                        recyclerView.setAdapter(adapter);
                        adapter.setOnShareClickedListener(TopMoviesFragment.this);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //https://api.themoviedb.org/3/nl79FQ8xWZkhL3rDr1v2RFFR6J0.jpg
        // https://image.tmdb.org/t/p/w780/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg
        private MovieDataSet fromJson(JSONObject jsonObject) {
            MovieDataSet d = new MovieDataSet();
            // Deserialize json into object fields
            try {
                d.setId(jsonObject.getString("id"));
                d.setName(jsonObject.getString("original_title"));
                d.setRating(jsonObject.getString("vote_average"));
                d.setPosterurl(jsonObject.getString("poster_path"));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            // Return new object
            return d;
        }
    }


}
