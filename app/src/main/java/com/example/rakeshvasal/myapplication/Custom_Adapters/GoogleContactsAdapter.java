package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.GPeople;
import com.example.rakeshvasal.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakeshvasal on 10-Mar-18.
 */

public class GoogleContactsAdapter extends RecyclerView.Adapter<GoogleContactsAdapter.MyViewHolder> {

    List<GPeople> gPeopleList = new ArrayList<>();
    Activity activity;
    OnShareClickedListener mCallback;

    public GoogleContactsAdapter(Activity activity, List<GPeople> gPeopleList) {
        this.activity = activity;
        this.gPeopleList = gPeopleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gpeople_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (gPeopleList != null) {
            if (gPeopleList.size() > 0) {
                GPeople gPeople = gPeopleList.get(position);
                //Log.e("pos", "" + position);
                if (gPeople.getNames() != null) {
                    holder.name.setText(gPeople.getNames().get(0).getDisplayName());
                }/*else {
                    holder.name.setText("No Display Name");
                }*/
            }
        }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.ShareClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gPeopleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            /*address = (TextView) itemView.findViewById(R.id.address);
            gender = (TextView) itemView.findViewById(R.id.gender);
            birthday = (TextView) itemView.findViewById(R.id.birthday);
            phone_no = (TextView) itemView.findViewById(R.id.phone_no);*/

        }
    }

    public interface OnShareClickedListener {
        public void ShareClicked(int match_id);
    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }

}
