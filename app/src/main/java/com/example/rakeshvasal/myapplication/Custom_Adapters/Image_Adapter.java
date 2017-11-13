package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rakeshvasal.myapplication.Activity.Image_Capture_Location;
import com.example.rakeshvasal.myapplication.GetterSetter.Image_Items;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.example.rakeshvasal.myapplication.Activity.ViewImageFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

        public MyViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.image_name);
            latitude = (TextView) view.findViewById(R.id.latitude);
            longitude = (TextView) view.findViewById(R.id.longitude);
            //thumbnail = (ImageView) view.findViewById(R.id.list_image);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(Image_Adapter.MyViewHolder holder, final int position) {

        /*if (images_path.size() <= 0) {



        } else {*/
            /*int size=images_path.size();
            for (int i = 0 ; i < size; i++) {*/
            try {
                holder.title.setText(Utils.Images_name_Array_List.get(position).toString());
                holder.latitude.setText(Utils.Images_latitude_Array_List.get(position).toString());
                holder.longitude.setText(Utils.Images_longitude_Array_List.get(position).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*try {
                fs = new FileInputStream(new File(images_path.get(position).toString()));

                if (fs != null) {
                    try {
                        bm = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        //Bitmap resized = Bitmap.createScaledBitmap(bm,(int)(bm.getWidth()*0.7), (int)(bm.getHeight()*0.7), true);
                        //imageView.setImageBitmap(resized);
                        bm.compress(Bitmap.CompressFormat.JPEG, 90, os);
                        byte[] bytes = os.toByteArray();
                        String image = Base64.encodeToString(bytes, Base64.DEFAULT);
                        byte[] bytesImage = Base64.decode(image, Base64.DEFAULT);
                        Glide.with(context).load(bytesImage).asBitmap().into(holder.thumbnail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //holder.thumbnail.setImageBitmap(bm);
                    //Glide.with(context).load(bm).into(holder.image.setImageBitmap(bitmap););
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fs != null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }*/

        //}

        /*holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewImageFragment.class);
                intent.putExtra("image_path",images_path.get(position).toString());
                context.startActivity(intent);
            }
        });*/
        /*}*/
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
