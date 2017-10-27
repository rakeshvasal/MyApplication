package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.content.Loader;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.Album;
import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.DateFormattingClass;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Axisvation on 10/27/2017.
 */

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder> {

    List<CricketMatch> matchesList;
    Context mContext;

    public MatchesAdapter(Context mContext, List<CricketMatch> matchesList ) {
        this.mContext = mContext;
        this.matchesList = matchesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type, team1, team2, matchstarted, date,winnerteam;

        public MyViewHolder(View itemView) {
            super(itemView);

            type = (TextView) itemView.findViewById(R.id.match_type);
            team1 = (TextView) itemView.findViewById(R.id.team_1);
            team2 = (TextView) itemView.findViewById(R.id.team_2);
            matchstarted = (TextView) itemView.findViewById(R.id.match_started);
            date = (TextView) itemView.findViewById(R.id.date);
            winnerteam  = (TextView) itemView.findViewById(R.id.winnerteam);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_card_list_items, parent, false);

        return new MatchesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CricketMatch cricketMatch = matchesList.get(position);
        holder.type.setText("Match Type : "+ cricketMatch.getType());
        String date = cricketMatch.getDate();
        date = date.substring(0,date.indexOf("T"));
        Log.d("date",date);
        DateFormattingClass formattingClass = new DateFormattingClass();
        try {
            date=formattingClass.formatDate(mContext,date,"yyyy-MM-dd","dd-MM-yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.winnerteam.setText("Winner Team : "+cricketMatch.getWinnerteam());
        holder.date.setText("Match Date : "+date);
        holder.team1.setText("Team 1 : "+cricketMatch.getTeam1()+cricketMatch.getUniqueId());
        holder.team2.setText("Team 2 : "+cricketMatch.getTeam2());
        holder.matchstarted.setText("Match Started : "+ cricketMatch.getMatchstarted());

    }


    @Override
    public int getItemCount() {
        return matchesList.size();
    }

}

