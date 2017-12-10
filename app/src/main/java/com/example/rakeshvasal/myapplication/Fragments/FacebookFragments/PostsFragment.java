package com.example.rakeshvasal.myapplication.Fragments.FacebookFragments;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.Activity.PosterActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends BaseFragment implements FBPhotoCustomAdapter.OnShareClickedListener {

    RecyclerView recyclerView;

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_posts, container, false);
        String type = getArguments().getString("type");
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        showProgressDialog();
        if (type.equalsIgnoreCase("1")) {
            getThreads();
        }else if (type.equalsIgnoreCase("2")){
            getPhotos();
        }
        return v;
    }
    private void getThreads() {

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.e("Responsefeed", response.toString());
                        JSONObject data = response.getJSONObject();
                        // posts.setText("Feed : " +data.toString());

                        try {
                            closeProgressDialog();
                            JSONObject jsonObject = new JSONObject(data.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<FBPosts> dataarray = new ArrayList<>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                // dataarray.add(i,jsonObject1.getString("story"));
                                String id = jsonObject1.getString("id");
                                String createdtime = jsonObject1.getString("created_time");
                                String story = jsonObject1.getString("story");
                                String message = "";
                                if (jsonObject1.has("message")) {
                                    message = jsonObject1.getString("message");
                                }
                                FBPosts fbPosts = new FBPosts(message, story, createdtime, id);
                                dataarray.add(fbPosts);
                            }


                            FBPostsCustomAdapter adapter = new FBPostsCustomAdapter(getActivity(), dataarray);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            shortToast(e.getMessage());
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("since", "1 january 2016");
        parameters.putString("until", "now");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getPhotos() {

        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/photos", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        closeProgressDialog();
                        Log.e("Responsephotos", response.toString());
                        JSONObject data = response.getJSONObject();

                        try {
                            JSONObject jsonObject = new JSONObject(data.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<FBPhotos> dataarray = new ArrayList<>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String id = jsonObject1.getString("id");
                                String pictureurl = jsonObject1.getString("picture");
                                String postlink = "";
                                if (jsonObject1.has("link")) {
                                    postlink = jsonObject1.getString("link");
                                } else {
                                    postlink = "";
                                }
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("from");
                                String postfrom = jsonObject2.getString("name");
                                String postfromid = jsonObject2.getString("id");
                                String albumname = "", albumid = "", albumcreatedate = "";
                                if (jsonObject1.has("album")) {
                                    JSONObject jsonObject3 = jsonObject1.getJSONObject("album");
                                    albumname = jsonObject3.getString("name");
                                    albumid = jsonObject3.getString("id");
                                    albumcreatedate = jsonObject3.getString("created_time");
                                }
                                FBPhotos fbPhotos = new FBPhotos(postfromid, postfrom, postlink, pictureurl, id, albumcreatedate, albumid, albumname);
                                dataarray.add(fbPhotos);
                            }
                            FBPhotoCustomAdapter adapter = new FBPhotoCustomAdapter(getActivity(), dataarray);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnShareClickedListener(PostsFragment.this);
                            adapter.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "album,id,from,link,picture,name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void ShareClicked(String url) {
        Intent intent = new Intent(getActivity(), PosterActivity.class);
        intent.putExtra("type","2");
        intent.putExtra("poster_url", url);
        startActivityForResult(intent, 1);
    }

    @Override
    public void getDetails(String id) {

    }

}