package com.example.rakeshvasal.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PosterActivity extends BaseActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        imageView = (ImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("poster_url");
        String image_url = Utils.MOVIEDB_PIC_BASE_URL + url;
        //showProgressDialog();
        new DownloadImage(PosterActivity.this, image_url).execute();

    }

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
            Glide.with(PosterActivity.this)
                    .load(image_url)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(680, 720) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
            closeProgressDialog();
        }
    }
}
