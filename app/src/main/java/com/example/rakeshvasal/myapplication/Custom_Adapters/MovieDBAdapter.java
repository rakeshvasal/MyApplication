package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Activity.PosterActivity;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieDataSet;
import com.example.rakeshvasal.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Axisvation on 12/2/2017.
 */

public class MovieDBAdapter extends RecyclerView.Adapter<MovieDBAdapter.MyViewHolder> {

    private List<MovieDataSet> dataSets;
    Context context;
    String type;
    OnShareClickedListener mCallback;

    public MovieDBAdapter(List<MovieDataSet> dataSets, Context context, String type) {
        this.dataSets = dataSets;
        this.context = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.datalistitem, parent, false);

        return new MovieDBAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (dataSets != null) {
            int i = position % 4;
            if (i == 0) {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            } else if (i == 1) {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.blue));
            } else if (i == 2) {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
            } else if (i == 3) {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.green));
            }
            MovieDataSet dataSet = dataSets.get(position);
            if (type.equalsIgnoreCase("mtoprated")) {
                holder.name.setText(dataSet.getName() + " (" + dataSet.getTitle() + ")");
                holder.rating.setText(dataSet.getRating());
                String htmlString="<u>Poster</u>";
                holder.poster.setText(Html.fromHtml(htmlString));
            }
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDataSet dataSet = dataSets.get(position);
                mCallback.getDetails(dataSet.getId());
            }
        });

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDataSet dataSet = dataSets.get(position);
                mCallback.ShareClicked(dataSet.getPosterurl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, rating, poster;
        ImageView imageView;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            rating = (TextView) itemView.findViewById(R.id.rating);
            poster = (TextView) itemView.findViewById(R.id.poster);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public interface OnShareClickedListener {
        public void ShareClicked(String url);
        public void getDetails(String id);
    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }

}
