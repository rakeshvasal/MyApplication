package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookHomeDashboard extends BaseFragment {

    TextView freindlist, posts, photos;
    RecyclerView recyclerView;

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
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freindlist.setVisibility(View.GONE);
                photos.setVisibility(View.GONE);
                showProgressDialog();
                getThreads();
            }
        });
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freindlist.setVisibility(View.GONE);
                posts.setVisibility(View.GONE);
                showProgressDialog();
                getPhotos();
            }
        });

        //
        //getFreindsList();

        return v;
    }

    private void getPhotos() {
        Profile profile = Profile.getCurrentProfile();
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/photos", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        closeProgressDialog();
                        Log.e("Responsephotos", response.toString());
                        JSONObject data = response.getJSONObject();
                        //photos.setText("Posts : " +data.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(data.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<FBPhotos> dataarray = new ArrayList<>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String id = jsonObject1.getString("id");
                                String pictureurl = jsonObject1.getString("picture");
                                String postlink = jsonObject.getString("link");
                                JSONObject jsonObject2 = jsonObject.getJSONObject("from");
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

                           /* StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < dataarray.size(); j++) {

                                stringBuilder.append(dataarray.get(j) + "\n");
                            }*/
                            /*photos.setText(stringBuilder.toString());*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*String json = new Gson().toJson(response);
                        Log.e("Responsephotosjson", response.toString());*/
                    }
                }

        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "album,id,from,link,picture,name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getThreads() {
        Profile profile = Profile.getCurrentProfile();
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

                            /*StringBuilder  stringBuilder = new StringBuilder();
                            for (int j=0;j<dataarray.size();j++){

                                stringBuilder.append(dataarray.get(j)+"\n");
                            }*/
                            FBPostsCustomAdapter adapter = new FBPostsCustomAdapter(getActivity(), dataarray);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            //posts.setText(stringBuilder.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("since", "1 january 2017");
        parameters.putString("until", "now");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getFreindsList() {
        GraphRequest request1 = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friendlists",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("FrndsResponse1", response.toString());
                        JSONObject data = response.getJSONObject();
                        freindlist.setText("FreindList : " + data.toString());
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,list_type");
        request1.setParameters(parameters);
        request1.executeAsync();


        GraphRequest request = new GraphRequest(

                AccessToken.getCurrentAccessToken(),
                "/me/permissions",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("FrndsResponse2", response.toString());
                    }
                }

        );

        request.executeAsync();
    }


}
