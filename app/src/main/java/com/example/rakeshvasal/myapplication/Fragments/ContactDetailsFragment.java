package com.example.rakeshvasal.myapplication.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.GPeople;
import com.example.rakeshvasal.myapplication.R;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailsFragment extends BaseFragment {


    String gPeopleJson;
    ImageView profilePicture;
    public ContactDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        Gson gson = new Gson();
        gPeopleJson=getArguments().getString("gPep");
        GPeople gPeople = gson.fromJson(String.valueOf(gPeopleJson), GPeople.class);
        String image_url = gPeople.getPhotos().get(0).getUrl();
        Log.i("picurl",gPeople.getPhotos().get(0).getUrl());
        profilePicture = (ImageView) view.findViewById(R.id.profilePicture);
        Glide.with(getActivity())
                .load(image_url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(600, 400) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        profilePicture.setImageBitmap(bitmap);
                    }
                });
        return view;
    }



}
