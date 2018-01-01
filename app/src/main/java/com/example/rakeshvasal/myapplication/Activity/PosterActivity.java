package com.example.rakeshvasal.myapplication.Activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PosterActivity extends BaseActivity {

    ImageView imageView;
    String image_url, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        imageView = (ImageView) findViewById(R.id.image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String type = bundle.getString("type");

        if (type.equalsIgnoreCase("1")) {
            url = bundle.getString("poster_url");
            image_url = Utils.MOVIEDB_PIC_BASE_URL + url;
        } else if (type.equalsIgnoreCase("2")) {
            image_url = bundle.getString("poster_url");
        }
        Glide.with(PosterActivity.this)
                .load(image_url)
                .asBitmap()
                .animate(animationObject)
                .into(new SimpleTarget<Bitmap>(680, 720) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {

                        imageView.setImageBitmap(bitmap);
                    }
                });
        //showProgressDialog();
        // new DownloadImage(PosterActivity.this, image_url).execute();

    }

    ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            // if it's a custom view class, cast it here
            // then find subviews and do the animations
            // here, we just use the entire view for the fade animation
            view.setAlpha(0f);

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            fadeAnim.setDuration(2500);
            fadeAnim.start();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.code:
                try {
                    //Utils.openSourceFile(Device_Info.this, "Device_Info", "java");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
