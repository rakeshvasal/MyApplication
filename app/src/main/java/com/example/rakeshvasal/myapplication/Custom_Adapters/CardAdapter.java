package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.CardItems;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Rakeshvasal on 08-Oct-17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<CardItems> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(List<CardItems> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,sub_title;
        ImageView item_image;
        // each data item is just a string in this case
        public ViewHolder(View view) {
            super(view);
            title =  (TextView) view.findViewById(R.id.title);
            sub_title =  (TextView) view.findViewById(R.id.sub_title);
            item_image = (ImageView) view.findViewById(R.id.item_image);
        }
    }
}
