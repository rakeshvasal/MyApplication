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
    String temp = "";

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
            if (position==0){
                holder.header.setVisibility(View.VISIBLE);
            }
            if (position > 0) {
                if (calenderMatches.get(position).getDate().equalsIgnoreCase(calenderMatches.get(position - 1).getDate())) {
                    holder.header.setVisibility(View.GONE);
                } else {
                    holder.header.setVisibility(View.VISIBLE);
                }
            }

            String matchname = calenderMatch.getName();
            int pos = matchname.indexOf("at");
            String match = matchname.substring(0, pos - 1);
            String place = matchname.substring(pos + 3, matchname.length());
            pos = place.indexOf(",");
            String details = place.substring(pos + 2, place.length());
            place = place.substring(0, pos);
            holder.name.setText("Match Details : \n" + match + " " + details);
            holder.venue.setText("Venue : " + place);
        }
    }

    @Override
    public int getItemCount() {
        return calenderMatches.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView header, name, venue;

        public ViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header);
            name = (TextView) itemView.findViewById(R.id.name);
            venue = (TextView) itemView.findViewById(R.id.venue);
        }
    }
}
