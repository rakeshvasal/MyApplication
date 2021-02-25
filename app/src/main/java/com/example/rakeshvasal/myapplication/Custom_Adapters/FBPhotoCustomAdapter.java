package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.rakeshvasal.myapplication.GetterSetter.FBPhotos;
import com.example.rakeshvasal.myapplication.R;

import java.util.List;

/**
 * Created by Rakeshvasal on 06-Dec-17.
 */

public class FBPhotoCustomAdapter extends RecyclerView.Adapter<FBPhotoCustomAdapter.MyViewHolder> {

    Context context;
    List<FBPhotos> datalist;
    OnShareClickedListener mCallback;
    public FBPhotoCustomAdapter(Context context, List<FBPhotos> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fbphotoslistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (datalist!=null){
            FBPhotos fbPhotos = datalist.get(position);
            holder.photoid.setText(fbPhotos.getId());
            String htmlString="<u>Image</u>";
           // holder.poster.setText(Html.fromHtml(htmlString));
            holder.photourl.setText(Html.fromHtml(htmlString));

            Glide.with(context)
                    .asBitmap()
                    .load(fbPhotos.getPictureurl())
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition anim) {

                            holder.bit_img.setImageBitmap(bitmap);
                        }
                    });
        }

        holder.photourl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FBPhotos dataSet = datalist.get(position);
                if (dataSet.getPictureurl()!=null)
                mCallback.ShareClicked(dataSet.getPictureurl(),dataSet.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView photoid,photourl;
        public ImageView bit_img;

        public MyViewHolder(View itemView) {
            super(itemView);

            photoid = (TextView) itemView.findViewById(R.id.photo_id);
            photourl = (TextView) itemView.findViewById(R.id.photo_url);
            bit_img = (ImageView) itemView.findViewById(R.id.bit_img);
        }
    }

    public interface OnShareClickedListener {
        public void ShareClicked(String url,String id);

    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }
}
