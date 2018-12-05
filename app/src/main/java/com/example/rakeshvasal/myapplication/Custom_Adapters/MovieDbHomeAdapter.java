package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rakeshvasal.myapplication.Fragments.TopMoviesFragment;
import com.example.rakeshvasal.myapplication.Fragments.TopTVShowsFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieModel;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 11/30/2017.
 */

public class MovieDbHomeAdapter extends RecyclerView.Adapter<MovieDbHomeAdapter.MyViewHolder>  {

    Context context;
    List<MovieModel> movieModels;
    FragmentManager fm;

    public MovieDbHomeAdapter(Context context, List<MovieModel> movieModels,FragmentManager fm) {

        this.context = context;
        this.movieModels = movieModels;
        this.fm=fm;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moviedblistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (movieModels != null) {
            if (movieModels.size() > 0) {
                MovieModel album = movieModels.get(position);
                holder.description.setText(album.getDescription());

                Glide.with(context).load(album.getThumbnail()).into(holder.imageView);
            }
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = movieModels.get(position).getDescription();
                clickListeners(s);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

   private  void clickListeners(String s){
       if (s.equalsIgnoreCase("Search Movie")){

       }else if (s.equalsIgnoreCase("Search Movie Star")){

       }else if (s.equalsIgnoreCase("Popular People")){

       }else if (s.equalsIgnoreCase("Top Movies")){
           FragmentTransaction transaction = fm.beginTransaction();
           Fragment fragment = new TopMoviesFragment();
           transaction.replace(R.id.fragment_container, fragment);
           transaction.commit();
       }else if (s.equalsIgnoreCase("Top TV Shows")){
           FragmentTransaction transaction = fm.beginTransaction();
           Fragment fragment = new TopTVShowsFragment();
           transaction.replace(R.id.fragment_container, fragment);
           transaction.commit();
       }else {
           Toast.makeText(context,"No Class Defined",Toast.LENGTH_SHORT).show();
       }


   }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView description;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.bitmap);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
