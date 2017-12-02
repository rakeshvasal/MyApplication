package com.example.rakeshvasal.myapplication.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.example.rakeshvasal.myapplication.Utilities.makeServiceCall;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopMoviesFragment extends BaseFragment {

    String concat_url;
    TextView filter;
    LinearLayout filterlayout;
    boolean isvisible = false;

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
        filterlayout = (LinearLayout) root.findViewById(R.id.filter_layout);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        operations();
        return root;
    }

    private void operations() {

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

        new getTopmovies(getActivity(), concat_url).execute();


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
        }

    }
}
