package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.rakeshvasal.myapplication.Activity.ImageActivity;
import com.example.rakeshvasal.myapplication.Activity.Image_Capture_Location;
import com.example.rakeshvasal.myapplication.GetterSetter.Image_Items;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakeshvasal on 31-Dec-16.
 */

public class Image_Adapter extends RecyclerView.Adapter<Image_Adapter.MyViewHolder> {

    ArrayList<Image_Items> Images;
    Context context;
    List<String> images_path;
    BitmapFactory.Options bfOptions;
    FileInputStream fs = null;
    private static LayoutInflater inflater = null;
    Bitmap bm;

    public Image_Adapter(Image_Capture_Location activity, List<String> images_path) {
        // TODO Auto-generated constructor stub
        this.images_path = images_path;
        this.context = activity;
        bfOptions = new BitmapFactory.Options();
       /* bfOptions.inPurgeable=true;
        bfOptions.inJustDecodeBounds = true;*/
        bfOptions.inJustDecodeBounds = false;
        bfOptions.inSampleSize = 32;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Image_Adapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, longitude, latitude;
        public ImageView thumbnail;
        CardView cv;
        LinearLayout card_item;

        public MyViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.image_name);
            latitude = (TextView) view.findViewById(R.id.latitude);
            longitude = (TextView) view.findViewById(R.id.longitude);
            card_item = (LinearLayout) view.findViewById(R.id.card_item);
            thumbnail = (ImageView) view.findViewById(R.id.list_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final Image_Adapter.MyViewHolder holder,int position) {

        final int pos=position;
        try {
            holder.title.setText("Name " + Utils.Images_name_Array_List.get(position));
            holder.latitude.setText("Lat " + Utils.Images_latitude_Array_List.get(position));
            holder.longitude.setText("Long " + Utils.Images_longitude_Array_List.get(position));
            String url = Utils.Images_url_Array_List.get(position);
            if (!url.equalsIgnoreCase("")) {
                Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(100, 100) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                holder.thumbnail.setImageBitmap(bitmap);
                            }
                        });
            }else{
                holder.thumbnail.setImageDrawable(context.getDrawable(R.drawable.locations));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.card_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Utils.Images_url_Array_List.get(pos);
                if (!url.equalsIgnoreCase("")) {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("source", "googleurl");
                    intent.putExtra("image_path", url);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context,"No Url",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return Utils.Images_latitude_Array_List.size();
        //return images_path.size();
    }


}
