package com.example.rakeshvasal.myapplication.Fragments.FacebookFragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.FBPhotoCustomAdapter;
import com.example.rakeshvasal.myapplication.Custom_Adapters.FBPostsCustomAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.FBPhotos;
import com.example.rakeshvasal.myapplication.GetterSetter.FBPosts;
import com.example.rakeshvasal.myapplication.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FacebookHomeDashboard extends BaseFragment {

    TextView freindlist, posts, photos, tagfreind, appfreinds,userbio;
    RecyclerView recyclerView;
    ProfileTracker mProfileTracker;

    public FacebookHomeDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_facebook_home_dashboard, container, false);

        freindlist = (TextView) v.findViewById(R.id.freindlist);
        posts = (TextView) v.findViewById(R.id.post);
        photos = (TextView) v.findViewById(R.id.photos);
        tagfreind = (TextView) v.findViewById(R.id.tagfreind);
        appfreinds = (TextView) v.findViewById(R.id.appfreinds);
        userbio = (TextView) v.findViewById(R.id.userbio);
        Profile profile = Profile.getCurrentProfile();
        if (profile == null) {
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    Log.v("facebook - profile", currentProfile.getFirstName());
                    mProfileTracker.stopTracking();
                    Profile.setCurrentProfile(currentProfile);
                }
            };
        }
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                arg.putString("type", "1");
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new PostsFragment();
                transaction.replace(((ViewGroup) getView().getParent()).getId(), fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                arg.putString("type", "2");
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new PostsFragment();
                transaction.replace(((ViewGroup) getView().getParent()).getId(), fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        freindlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
                arg.putString("type", "2");
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new TaggableFreindsFragment();
                transaction.replace(((ViewGroup) getView().getParent()).getId(), fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        tagfreind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
                arg.putString("type", "1");
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new TaggableFreindsFragment();
                transaction.replace(((ViewGroup) getView().getParent()).getId(), fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        appfreinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
                arg.putString("type", "3");
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new TaggableFreindsFragment();
                transaction.replace(((ViewGroup) getView().getParent()).getId(), fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        /*userbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraphRequest request = GraphRequest.newGraphPathRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me",
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                Log.e("BIO", ""+response.getJSONObject());
                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "about,address,birthday,location");
                request.setParameters(parameters);
                request.executeAsync();
            }
        });*/
        userbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraphRequest request = GraphRequest.newGraphPathRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/objects",
                        //"/me/feed",
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                Log.e("BIO", ""+response.getJSONObject());
                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("type", "comment");
                //parameters.putString("fields", "id");
                request.setParameters(parameters);
                request.executeAsync();
            }
        });
        return v;
    }


}
