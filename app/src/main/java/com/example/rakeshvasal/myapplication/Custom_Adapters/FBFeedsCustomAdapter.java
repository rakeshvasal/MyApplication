package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.FBFeeds;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Axisvation on 12/6/2017.
 */

public class FBFeedsCustomAdapter extends RecyclerView.Adapter<FBFeedsCustomAdapter.MyViewHolder> {

    Context context;
    List<FBFeeds> fbPosts;
    OnShareClickedListener mCallback;
    public FBFeedsCustomAdapter(Context context, List<FBFeeds> fbPosts) {
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
    public void onBindViewHolder(FBFeedsCustomAdapter.MyViewHolder holder, final int position) {
        if (fbPosts != null) {
            FBFeeds fbPostsdata = fbPosts.get(position);
            holder.Id.setText(fbPostsdata.getId());
            holder.story.setText(fbPostsdata.getStory());
            holder.createddate.setText(fbPostsdata.getCreatedtime());
            if (fbPostsdata.getMessage()!=null&&!fbPostsdata.getMessage().equalsIgnoreCase("")){
                holder.message.setText(fbPostsdata.getMessage());
            }
            if (fbPostsdata.getStory_tags()!=null&&!fbPostsdata.getStory_tags().equalsIgnoreCase("")){
                holder.message.setText(fbPostsdata.getMessage());
            }
            if (fbPostsdata.getFull_picture()!=null&&!fbPostsdata.getFull_picture().equalsIgnoreCase("")){
                String htmlString="<u>Image</u>";
                // holder.poster.setText(Html.fromHtml(htmlString));
                holder.image_url.setText(Html.fromHtml(htmlString));
            }

        }
        holder.image_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FBFeeds dataSet = fbPosts.get(position);
                if (dataSet.getFull_picture()!=null)
                    mCallback.ShareClicked(dataSet.getFull_picture(),dataSet.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return fbPosts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Id, message, story, createddate,likes,reactions,image_url;

        public MyViewHolder(View itemView) {
            super(itemView);
            Id = (TextView) itemView.findViewById(R.id.ID);
            message = (TextView) itemView.findViewById(R.id.message);
            story = (TextView) itemView.findViewById(R.id.story);
            createddate = (TextView) itemView.findViewById(R.id.createddate);
            likes = (TextView) itemView.findViewById(R.id.likes);
            reactions = (TextView) itemView.findViewById(R.id.reactions);
            image_url = (TextView) itemView.findViewById(R.id.image_url);
        }
    }

    public interface OnShareClickedListener {
        public void ShareClicked(String url,String id);

    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }
}
