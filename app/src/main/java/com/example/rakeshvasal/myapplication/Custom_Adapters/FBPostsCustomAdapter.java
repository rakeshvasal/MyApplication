package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.FBPosts;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 12/6/2017.
 */

public class FBPostsCustomAdapter extends RecyclerView.Adapter<FBPostsCustomAdapter.MyViewHolder> {

    Context context;
    List<FBPosts> fbPosts;

    public FBPostsCustomAdapter(Context context, List<FBPosts> fbPosts) {
        this.context = context;
        this.fbPosts = fbPosts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fbpostslistitem, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(FBPostsCustomAdapter.MyViewHolder holder, int position) {
        if (fbPosts != null) {
            FBPosts fbPostsdata = fbPosts.get(position);
            holder.Id.setText(fbPostsdata.getId());
            holder.story.setText(fbPostsdata.getStory());
            holder.createddate.setText(fbPostsdata.getCreatedtime());
            if (!fbPostsdata.getMessage().equalsIgnoreCase("")){
                holder.message.setText(fbPostsdata.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return fbPosts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Id, message, story, createddate;

        public MyViewHolder(View itemView) {
            super(itemView);
            Id = (TextView) itemView.findViewById(R.id.ID);
            message = (TextView) itemView.findViewById(R.id.message);
            story = (TextView) itemView.findViewById(R.id.story);
            createddate = (TextView) itemView.findViewById(R.id.createddate);


        }
    }
}
