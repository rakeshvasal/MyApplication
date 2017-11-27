package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.util.List;

/**
 * Created by Rakeshvasal on 26-Nov-17.
 */

public class EventSelectAdapter extends RecyclerView.Adapter<EventSelectAdapter.MyViewHolder> {

    Activity activity;
    List<Events> events;

    public EventSelectAdapter(Activity activity, List<Events> events) {
        this.activity = activity;
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventselectlistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (events != null && events.size() > 0) {
            Events eventelement = events.get(position);
            holder.name.setText(eventelement.getEventName());
        }
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    Events eventsobj = events.get(position);
                    String name = eventsobj.getEventName();
                    String amount = eventsobj.getEntryFees();
                    Utils.eventselected.add(name);
                    Utils.eventselectedamounts.add(amount);
                } else {
                    Events eventsobj = events.get(position);
                    String name = eventsobj.getEventName();
                    String amount = eventsobj.getEntryFees();
                    Utils.eventselected.remove(name);
                    Utils.eventselectedamounts.remove(amount);
                }
                events.get(position).setSelectedposition(compoundButton.isChecked());
            }
        });

        holder.check.setChecked(events.get(position).getSelectedposition());

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CheckBox check;


        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.eventname);
            check = (CheckBox) itemView.findViewById(R.id.check);
        }
    }
}
