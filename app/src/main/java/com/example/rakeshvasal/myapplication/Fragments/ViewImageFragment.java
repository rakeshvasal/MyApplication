package com.example.rakeshvasal.myapplication.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;


public class ViewImageFragment extends BaseFragment {

    ImageView imageView;
    BitmapFactory.Options bfOptions;
    Bitmap bm;
    FileInputStream fs = null;
    Activity activity;
    BottomNavigationView navigation;
    String image_path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_image, container, false);

        bfOptions = new BitmapFactory.Options();
       /* bfOptions.inPurgeable=true;
        bfOptions.inJustDecodeBounds = true;*/
        bfOptions.inJustDecodeBounds = false;
        bfOptions.inSampleSize = 32;
        image_path = getArguments().getString("image_path");
        Log.d("image_path", "" + image_path);

        navigation = (BottomNavigationView) root.findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.album1);
        requestOptions.error(R.drawable.album1);
        imageView = (ImageView) root.findViewById(R.id.image_view);
        try {
            if (image_path != null && !image_path.equalsIgnoreCase("")) {
                Glide.with(getActivity())
                        .asBitmap()
                        .load(image_path)
                        .apply(requestOptions)
                        .into(new SimpleTarget<Bitmap>(600, 400) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition anim) {
                                imageView.setImageBitmap(bitmap);
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                log("image_loading_started");
                                imageView.setImageDrawable(placeholder);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                imageView.setImageDrawable(errorDrawable);
                            }
                        });
                //new DownloadImage(getActivity(),image_path).execute();
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
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle arg = new Bundle();
                    arg.putString("image_path", image_path);
                    android.app.Fragment fragment = new ImageAnalysisFragment();
                    transaction.replace(R.id.fragment_container, fragment);
                    fragment.setArguments(arg);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    return true;
                case R.id.bottom_navigation_details:

                    return true;
            }

            return false;
        }

    };

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        Context context;
        String image_url;
        Bitmap img;

        DownloadImage(Context context, String image_url) {
            this.context = context;
            this.image_url = image_url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                InputStream in = new java.net.URL(image_url).openStream();
                img = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return img;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, os);
            byte[] bytes = os.toByteArray();
            String image = Base64.encodeToString(bytes, Base64.DEFAULT);
            byte[] bytesImage = Base64.decode(image, Base64.DEFAULT);

            closeProgressDialog();
        }
    }

}
