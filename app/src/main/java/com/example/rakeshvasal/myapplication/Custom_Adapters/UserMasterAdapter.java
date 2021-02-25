package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments.AddUpdateUserFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.util.List;

/**
 * Created by Axisvation on 11/7/2017.
 */

public class UserMasterAdapter extends RecyclerView.Adapter<UserMasterAdapter.MyViewHolder> {

    Activity activity;
    List<User> users;
    FragmentManager fm;
    public UserMasterAdapter(Activity activity, List<User> users, FragmentManager fm) {
        this.activity = activity;
        this.users=users;
        this.fm=fm;
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

            final User userelement = users.get(position);
            //Log.d("eventname",eventelement.getEventName());
            holder.name.setText(userelement.getUser_name());
            holder.email.setText(userelement.getUser_email());
            holder.contact.setText(userelement.getContact_no());

            holder.ic_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle arg = new Bundle();
                    arg.putString("userid", userelement.getUser_id());
                    arg.putString(Utils.TASK, Utils.UPDATE_TASK);
                    FragmentTransaction transaction = fm.beginTransaction();
                    Fragment fragment = new AddUpdateUserFragment();
                    fragment.setArguments(arg);
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView email, name, contact;
        LinearLayout ll_main;
        ImageView ic_edit;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email_id);
            contact = (TextView) itemView.findViewById(R.id.contact);
            ic_edit = (ImageView) itemView.findViewById(R.id.edit);
        }
    }
}
