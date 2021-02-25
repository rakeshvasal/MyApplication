package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.ComitteeMembers;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 11/28/2017.
 */

public class CommitteeMemberAdapter extends RecyclerView.Adapter<CommitteeMemberAdapter.MyViewHolder> {

    private List<ComitteeMembers> members;
    Context context;

    public CommitteeMemberAdapter(List<ComitteeMembers> members, Context context) {
        this.members = members;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.membermasterlistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (members!=null){

            ComitteeMembers membersitem = members.get(position);
            holder.name.setText(membersitem.getMemberName());
            holder.contact.setText(membersitem.getContact_no());
        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,contact;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            contact = (TextView) itemView.findViewById(R.id.contact);

        }
    }
}
