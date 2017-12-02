package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.MovieDataSet;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 12/2/2017.
 */

public class MovieDBAdapter extends RecyclerView.Adapter<MovieDBAdapter.MyViewHolder> {

    private List<MovieDataSet> dataSets;
    Context context;
    String type;

    public MovieDBAdapter(List<MovieDataSet> dataSets, Context context,String type) {
        this.dataSets = dataSets;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.datalistitem, parent, false);

        return new MovieDBAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (dataSets!=null){
            MovieDataSet dataSet = dataSets.get(position);

            holder.name.setText(dataSet.getName());
            holder.rating.setText(dataSet.getRating());
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,rating;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            rating = (TextView) itemView.findViewById(R.id.rating);

        }
    }
}
