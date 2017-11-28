package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.GetterSetter.ComitteeMembers;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 11/28/2017.
 */

public class CommitteeMemberAdapter extends RecyclerView.Adapter<CommitteeMemberAdapter.MyViewHolder> {

    private List<ComitteeMembers> members;


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventselectlistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);


        }
    }
}
