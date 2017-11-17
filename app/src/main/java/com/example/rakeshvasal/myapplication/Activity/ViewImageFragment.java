package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;


public class ViewImageFragment extends BaseFragment {

    ImageView imageView;
    BitmapFactory.Options bfOptions;
    Bitmap bm;
    FileInputStream fs = null;
    Activity activity;
    BottomNavigationView navigation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_image, container, false);

        bfOptions = new BitmapFactory.Options();
       /* bfOptions.inPurgeable=true;
        bfOptions.inJustDecodeBounds = true;*/
        bfOptions.inJustDecodeBounds = false;
        bfOptions.inSampleSize = 32;
       String image_path = getArguments().getString("image_path");
        Log.d("image_path", "" + image_path);

        navigation = (BottomNavigationView) root.findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imageView = (ImageView) root.findViewById(R.id.image_view);
        try {
            if (image_path != null && !image_path.equalsIgnoreCase("")) {
                fs = new FileInputStream(new File(image_path));
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.bottom_navigation_scan:

                    return true;
                case R.id.bottom_navigation_details:

                    return true;
            }

            return false;
        }

    };


}
