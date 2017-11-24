package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 11/24/2017.
 */

public class LocationMasterAdapter extends RecyclerView.Adapter<LocationMasterAdapter.MyViewHolder> {

    Activity activity;
    String[] name;

    public LocationMasterAdapter(Activity activity, String[] name) {
        this.activity = activity;
        this.name = name;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.locationlistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvname;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.name);
        }
    }
    @Override
    public void onBindViewHolder(LocationMasterAdapter.MyViewHolder holder, int position) {

        if (name != null) {
            holder.tvname.setText(name[position]);
        }

    }

    @Override
    public int getItemCount() {
        return name.length;
    }


}
