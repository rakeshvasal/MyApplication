package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.rakeshvasal.myapplication.GetterSetter.Album;
import com.example.rakeshvasal.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Rakeshvasal on 22-Oct-17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;
    FileInputStream fs = null;
    Bitmap bm;
    BitmapFactory.Options bfOptions;
    int i = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);

            thumbnail = (ImageView) view.findViewById(R.id.card_imageview);
            // overflow = (ImageView) view.findViewById(R.id.overflow);
        }


    }

    public GalleryAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
        bfOptions = new BitmapFactory.Options();
        bfOptions.inJustDecodeBounds = false;
        bfOptions.inSampleSize = 32;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_items, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final GalleryAdapter.MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        //holder.title.setText(album.getName());
        // holder.count.setText(album.getNumOfSongs() + " songs");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.album1);
        requestOptions.error(R.drawable.album1);
        // loading album cover using Glide library
        String photopath = album.getThumbnail();
        try {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bm.compress(Bitmap.CompressFormat.PNG, 10, stream);
            Glide.with(mContext)
                    .asBitmap()
                    .load(photopath)
                    .apply(requestOptions)
                    .into(new SimpleTarget<Bitmap>(100, 100) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition anim) {

                            holder.thumbnail.setImageBitmap(bitmap);
                        }
                    });
            if (i % 10 == 0) {
                //Toast.makeText(mContext,""+i,Toast.LENGTH_SHORT).show();
            }
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
