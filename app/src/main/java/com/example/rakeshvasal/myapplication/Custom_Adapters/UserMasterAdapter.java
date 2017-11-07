package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 11/7/2017.
 */

public class UserMasterAdapter extends RecyclerView.Adapter<UserMasterAdapter.MyViewHolder> {

    Activity activity;
    List<User> users;

    public UserMasterAdapter(Activity activity, List<User> users) {
        this.activity = activity;
        this.users=users;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usermasterlistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(users.size()>0){

            User userelement = users.get(position);
            //Log.d("eventname",eventelement.getEventName());
            holder.name.setText(userelement.getUser_name());
            holder.email.setText(userelement.getUser_email());
            holder.contact.setText(userelement.getContact_no());

        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView email, name, contact;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email_id);
            contact = (TextView) itemView.findViewById(R.id.contact);
        }
    }
}
