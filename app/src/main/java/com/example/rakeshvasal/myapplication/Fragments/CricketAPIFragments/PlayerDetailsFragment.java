package com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.rakeshvasal.myapplication.Activity.PosterActivity;
import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.Player;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayerDetailsFragment extends BaseFragment {

    String API_KEY = "";
    private ApiInterface apiInterface;
    TextView name,profile,country,birthdate;
    ImageView profile_img;

    public PlayerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_player_details, container, false);
        name = (TextView) view.findViewById(R.id.full_name);
        profile = (TextView) view.findViewById(R.id.profileInfo);
        country = (TextView) view.findViewById(R.id.country);
        birthdate = (TextView) view.findViewById(R.id.bithdate);
        profile_img = (ImageView) view.findViewById(R.id.iv_profile_image);

        API_KEY = getResources().getString(R.string.cricapikey);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.CRIC_INFO_BASE_URL).create(ApiInterface.class);
        String id = getArguments().getString("pid");
        Call<JsonObject> details = apiInterface.getPlayerDetails(API_KEY, id);
        try {
            showProgressDialog();
            details.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        closeProgressDialog();
                        JsonObject jsonObject = response.body();
                        Gson gson = new Gson();
                        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                        Player obj = gson.fromJson("" + jsonObject1, Player.class);
                        setData(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    log("onFailure", t);
                    closeProgressDialog();
                }
            });
        } catch (Exception e) {

        }
        return view;
    }

    private void setData(Player player){
        name.setText(player.getFullname());
        profile.setText(player.getProfile());
        country.setText(player.getCountry());
        birthdate.setText(player.getBorn());
        Glide.with(getActivity())
                .load(player.getImageURL())
                .asBitmap()
                .animate(animationObject)
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {

                        profile_img.setImageBitmap(bitmap);
                    }
                });
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

}
