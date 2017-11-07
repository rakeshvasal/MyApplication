package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
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

    OnShareClickedListener mCallback;

    public MatchesAdapter(Context mContext, List<CricketMatch> matchesList) {
        this.mContext = mContext;
        this.matchesList = matchesList;

    }
    public interface OnShareClickedListener {
        public void ShareClicked(int match_id);
    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type, team1, team2, matchstarted, date, winnerteam, moreInfo;

        public MyViewHolder(View itemView) {
            super(itemView);

            type = (TextView) itemView.findViewById(R.id.match_type);
            team1 = (TextView) itemView.findViewById(R.id.team_1);
            team2 = (TextView) itemView.findViewById(R.id.team_2);
            matchstarted = (TextView) itemView.findViewById(R.id.match_started);
            date = (TextView) itemView.findViewById(R.id.date);
            winnerteam = (TextView) itemView.findViewById(R.id.winnerteam);
            moreInfo = (TextView) itemView.findViewById(R.id.moreInfo);
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

        final CricketMatch cricketMatch = matchesList.get(position);
        holder.type.setText("Match Type : " + cricketMatch.getType());
        String date = cricketMatch.getDate();
        date = date.substring(0, date.indexOf("T"));
        Log.d("date", date);
        DateFormattingClass formattingClass = new DateFormattingClass();
        try {
            date = formattingClass.formatDate(mContext, date, "yyyy-MM-dd", "dd-MM-yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.winnerteam.setText("Winner Team : " + cricketMatch.getWinnerteam() +" "+cricketMatch.getUniqueId());
        holder.date.setText("Match Date : " + date);
        holder.team1.setText("Team 1 : " + cricketMatch.getTeam1());
        holder.team2.setText("Team 2 : " + cricketMatch.getTeam2());
        holder.matchstarted.setText("Match Started : " + cricketMatch.getMatchstarted());

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int match_id = cricketMatch.getUniqueId();
                mCallback.ShareClicked(match_id);
            }
        });

    }




    @Override
    public int getItemCount() {
        return matchesList.size();
    }


}

