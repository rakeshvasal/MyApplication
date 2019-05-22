package com.example.rakeshvasal.myapplication.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.Set;

public class PosterActivity extends BaseActivity {

    ImageView imageView;
    String image_url, url, id;

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
            id = bundle.getString("photo_id");
        }
        Glide.with(PosterActivity.this)
                .asBitmap()
                .load(image_url)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition anim) {

                        imageView.setImageBitmap(bitmap);
                    }
                });
        if (type.equalsIgnoreCase("2")) {
            Set<String> permissions = AccessToken.getCurrentAccessToken().getDeclinedPermissions();
            logInfo(permissions.toString());

            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + id + "/likes",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Log.e("photodata", response.toString());
                        }
                    }
            ).executeAsync();
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
