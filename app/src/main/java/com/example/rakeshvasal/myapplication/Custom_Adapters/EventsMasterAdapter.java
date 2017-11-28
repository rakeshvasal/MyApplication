package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Rakeshvasal on 02-Nov-17.
 */

public class EventsMasterAdapter extends RecyclerView.Adapter<EventsMasterAdapter.MyViewHolder> {

    Activity activity;
    List<Events> events;

    public EventsMasterAdapter(Activity activity, List<Events> events) {
        this.activity = activity;
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventslistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsMasterAdapter.MyViewHolder holder, int position) {

        if (events.size() > 0) {

            Events eventelement = events.get(position);
            //Log.d("eventname",eventelement.getEventName());
            holder.name.setText(eventelement.getEventName());
            holder.venue.setText(eventelement.getLocation());
            holder.contact.setText(eventelement.getContactPerson());
            holder.entryfees.setText(eventelement.getEntryFees());
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView entryfees, venue, name, contact;
        public ImageView edit;


        public MyViewHolder(View itemView) {
            super(itemView);

            entryfees = (TextView) itemView.findViewById(R.id.fees);
            name = (TextView) itemView.findViewById(R.id.name);
            venue = (TextView) itemView.findViewById(R.id.venue);
            contact = (TextView) itemView.findViewById(R.id.contact);
            edit = (ImageView) itemView.findViewById(R.id.edit);
        }
    }
}
