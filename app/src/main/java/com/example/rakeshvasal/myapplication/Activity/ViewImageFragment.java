package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rakeshvasal.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;


public class ViewImageFragment extends AppCompatActivity {

    ImageView imageView;
    BitmapFactory.Options bfOptions;
    Bitmap bm;
    FileInputStream fs = null;
    Activity activity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_image);
        bfOptions = new BitmapFactory.Options();
       /* bfOptions.inPurgeable=true;
        bfOptions.inJustDecodeBounds = true;*/
        bfOptions.inJustDecodeBounds = false;
        bfOptions.inSampleSize = 32;
        Intent intent=getIntent();
        Bundle b = intent.getExtras();
        String path  = b.getString("image_path");
        activity = ViewImageFragment.this;


        imageView = (ImageView) findViewById(R.id.image_view);
        try {
            if (path != null && !path.equalsIgnoreCase("")) {
                fs = new FileInputStream(new File(path));
                bm = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                //Bitmap resized = Bitmap.createScaledBitmap(bm,(int)(bm.getWidth()*0.7), (int)(bm.getHeight()*0.7), true);
                //imageView.setImageBitmap(resized);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
                byte[] bytes = os.toByteArray();
                String image = Base64.encodeToString(bytes, Base64.DEFAULT);
                byte[] bytesImage = Base64.decode(image, Base64.DEFAULT);
                Glide.with(activity).load(bytesImage).asBitmap().into(imageView);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
