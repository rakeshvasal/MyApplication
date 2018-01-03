package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.Player;
import com.example.rakeshvasal.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axisvation on 1/3/2018.
 */

public class FindPlayerAdapter extends RecyclerView.Adapter<FindPlayerAdapter.MyViewHolder> {

    List<Player> playerList = new ArrayList<>();
    Context context;
    OnShareClickedListener mCallback;

    public FindPlayerAdapter(List<Player> playerList, Context context) {
        this.playerList = playerList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.findplayerlistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (playerList.size() > 0) {
            Player player = playerList.get(position);

            holder.id.setText(player.getId());
            holder.name.setText(player.getName());
            holder.fullname.setText(player.getFullname());

        }
        holder.ll_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.ShareClicked(playerList.get(position).getId());
            }
        });


    }


    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, fullname;
        LinearLayout ll_data;

        public MyViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            fullname = (TextView) itemView.findViewById(R.id.full_name);
            ll_data = (LinearLayout) itemView.findViewById(R.id.ll_data);
        }
    }

    public interface OnShareClickedListener {
        public void ShareClicked(String match_id);
    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }

}
