package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.CalenderMatch;
import com.example.rakeshvasal.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakeshvasal on 01-Jan-18.
 */

public class MatchCalenderAdapter extends RecyclerView.Adapter<MatchCalenderAdapter.ViewHolder> {

    List<CalenderMatch> calenderMatches = new ArrayList<>();
    Context context;
    String temp="";

    public MatchCalenderAdapter(Context context, List<CalenderMatch> calenderMatches) {
        this.context = context;
        this.calenderMatches = calenderMatches;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calenderlistitem, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (calenderMatches.size() > 0) {

            CalenderMatch calenderMatch = calenderMatches.get(position);
            holder.header.setText(calenderMatch.getDate());
            holder.name.setText(calenderMatch.getName());
        }
    }

    @Override
    public int getItemCount() {
        return calenderMatches.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView header, name;

        public ViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
