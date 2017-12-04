package com.example.rakeshvasal.myapplication.Fragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.EventsMasterAdapter;
import com.example.rakeshvasal.myapplication.Custom_Adapters.MovieDbHomeAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieModel;
import com.example.rakeshvasal.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDb_HomeFragment extends BaseFragment {

    RecyclerView recyclerView;
    List<MovieModel> movieModels;
    FragmentManager fragmentManager;
    public MovieDb_HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie_db_home, container, false);
        movieModels = new ArrayList<>();
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentManager=getFragmentManager();
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        MovieModel a = new MovieModel("Search Movie", covers[0]);
        movieModels.add(a);
        a = new MovieModel("Search Movie Star",covers[1]);
        movieModels.add(a);
        a = new MovieModel("Top Movies",covers[2]);
        movieModels.add(a);
        a = new MovieModel("Top TV Shows",covers[3]);
        movieModels.add(a);
        a = new MovieModel("Popular People",covers[4]);
        movieModels.add(a);

        MovieDbHomeAdapter adapter = new MovieDbHomeAdapter(getActivity(),movieModels,fragmentManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return root;
    }

}
