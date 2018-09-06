package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.GPeople;
import com.example.rakeshvasal.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakeshvasal on 10-Mar-18.
 */

public class GoogleContactsAdapter extends RecyclerView.Adapter<GoogleContactsAdapter.MyViewHolder> {

    private List<GPeople> gPeopleList = new ArrayList<>();
    private Activity activity;
    private OnShareClickedListener mCallback;

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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (gPeopleList != null) {
            if (gPeopleList.size() > 0) {
                GPeople gPeople = gPeopleList.get(holder.getAdapterPosition());
                //Log.e("pos", "" + position);
                if (gPeople.getNames() != null) {
                    holder.name.setText(holder.getAdapterPosition() + 1 + " : " + gPeople.getNames().get(0).getDisplayName());

                } else {
                    holder.name.setText(R.string.no_value);
                }
                if (gPeople.getPhoneNumbers() != null) {
                    holder.phone_no.setText(gPeople.getPhoneNumbers().get(0).getValue());
                    holder.phone_no.setTextColor(activity.getResources().getColor(R.color.black));
                } else {
                    holder.phone_no.setTextColor(activity.getResources().getColor(R.color.red));
                    holder.phone_no.setText(R.string.no_value);
                }
            }
        }
        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.ShareClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return gPeopleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone_no;
        LinearLayout ll_details;

        public MyViewHolder(View itemView) {
            super(itemView);

            ll_details = (LinearLayout) itemView.findViewById(R.id.ll_details);
            name = (TextView) itemView.findViewById(R.id.name);
            phone_no = (TextView) itemView.findViewById(R.id.phone_no);
            /*address = (TextView) itemView.findViewById(R.id.address);
            gender = (TextView) itemView.findViewById(R.id.gender);
            birthday = (TextView) itemView.findViewById(R.id.birthday);
           */

        }
    }

    public interface OnShareClickedListener {
        void ShareClicked(int match_id);
    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }

}
