package com.example.rakeshvasal.myapplication.Fragments.FacebookFragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.FBFreinds;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.makeServiceCall;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TaggableFreindsFragment extends BaseFragment {

    ListView text_list_view;
    List<FBFreinds> freindlistarray;
    String nextstring = "",previousstring="";

    public TaggableFreindsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_taggable_freinds, container, false);
        final String type = getArguments().getString("type");
        text_list_view = (ListView) v.findViewById(R.id.text_list_view);
        showProgressDialog();
        if (type.equalsIgnoreCase("1")) {
            getTaggableFreinds();
        } else if (type.equalsIgnoreCase("2")) {
            getFreindsList();
        } else if (type.equalsIgnoreCase("3")) {
            getFreinds();
        }
        TextView nextpages = (TextView) v.findViewById(R.id.nextpages);
        nextpages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                new getNextorPrevious(TaggableFreindsFragment.this).execute();

            }
        });


        text_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (type.equalsIgnoreCase("3")) {
                    FBFreinds fbFreinds = freindlistarray.get(i);
                    getIndividualdetails(fbFreinds.getId());
                }
            }
        });

        return v;
    }

    public void onBackgroundTaskCompleted(String s) {
        Log.d("nextdata", s);
        closeProgressDialog();
        try {
            JSONObject jsonObject = new JSONObject(s.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            //String[] dataarray = new String[jsonArray.length()];
            List<String> dataarray = new ArrayList<>(jsonArray.length());
            freindlistarray = new ArrayList<FBFreinds>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                // dataarray.add(i,jsonObject1.getString("story"));
                String id = jsonObject1.getString("id");
                String name = jsonObject1.getString("name");
                FBFreinds fbfrnds = new FBFreinds();
                fbfrnds.setId(id);
                fbfrnds.setName(name);
                String datavalue = i + 1 + " : " + name + "\n" + id;
                dataarray.add(datavalue);
                freindlistarray.add(fbfrnds);
            }

            if (jsonObject.has("paging")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("paging");
                if (jsonObject1.has("next")) {
                    nextstring = jsonObject1.getString("next");
                }
            }
            if (jsonObject.has("paging")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("paging");
                if (jsonObject1.has("previous")) {
                    previousstring = jsonObject1.getString("previous");
                }
            }
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.fbphotoslistitem, R.id.photo_id, dataarray);
            text_list_view.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getTaggableFreinds() {
        GraphRequest request1 = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/taggable_friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        try {
                            closeProgressDialog();
                            Log.e("taggablefrnds", response.toString());
                            JSONObject data = response.getJSONObject();
                            JSONObject jsonObject = new JSONObject(data.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            //String[] dataarray = new String[jsonArray.length()];
                            List<String> dataarray = new ArrayList<>(jsonArray.length());
                            freindlistarray = new ArrayList<FBFreinds>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                // dataarray.add(i,jsonObject1.getString("story"));
                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("name");
                                FBFreinds fbfrnds = new FBFreinds();
                                fbfrnds.setId(id);
                                fbfrnds.setName(name);
                                String datavalue = i + 1 + " : " + name + "\n" + id;
                                dataarray.add(datavalue);
                                freindlistarray.add(fbfrnds);
                            }

                            if (jsonObject.has("paging")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("paging");
                                if (jsonObject1.has("next")) {
                                    nextstring = jsonObject1.getString("next");
                                }
                            }
                            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.fbphotoslistitem, R.id.photo_id, dataarray);
                            text_list_view.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            shortToast(e.getMessage());
                        }

                        //freindlist.setText("Freinds : " + data.toString());
                    }

                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
        request1.setParameters(parameters);
        request1.executeAsync();

    }

    private void getFreindsList() {
        GraphRequest request1 = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friendlists",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            closeProgressDialog();
                            Log.e("freindlist", response.toString());
                            JSONObject data = response.getJSONObject();
                            JSONObject jsonObject = new JSONObject(data.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            //String[] dataarray = new String[jsonArray.length()];
                            List<String> dataarray = new ArrayList<>(jsonArray.length());
                            freindlistarray = new ArrayList<FBFreinds>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                // dataarray.add(i,jsonObject1.getString("story"));
                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("name");
                                String type = jsonObject1.getString("list_type");
                                FBFreinds fbfrnds = new FBFreinds();
                                fbfrnds.setId(id);
                                fbfrnds.setName(name);
                                fbfrnds.setList_name(type);
                                String datavalue = i + 1 + " : " + name + "\n" + id + "\n" + type;
                                dataarray.add(datavalue);
                                freindlistarray.add(fbfrnds);
                            }

                            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.fbphotoslistitem, R.id.photo_id, dataarray);
                            text_list_view.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            shortToast(e.getMessage());
                        }
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,list_type");
        request1.setParameters(parameters);
        request1.executeAsync();


    }

    private void getFreinds() {
        GraphRequest request1 = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            closeProgressDialog();
                            Log.e("frndsonapp", response.toString());
                            JSONObject data = response.getJSONObject();
                            JSONObject jsonObject = new JSONObject(data.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            //String[] dataarray = new String[jsonArray.length()];
                            List<String> dataarray = new ArrayList<>(jsonArray.length());
                            freindlistarray = new ArrayList<FBFreinds>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                // dataarray.add(i,jsonObject1.getString("story"));
                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("name");
                                FBFreinds fbfrnds = new FBFreinds();
                                fbfrnds.setId(id);
                                fbfrnds.setName(name);
                                String datavalue = name + "\n" + id;
                                dataarray.add(datavalue);
                                freindlistarray.add(fbfrnds);
                            }

                            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.fbphotoslistitem, R.id.photo_id, dataarray);
                            text_list_view.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            shortToast(e.getMessage());
                        }
                        //freindlist.setText("Freinds : " + data.toString());
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
        request1.setParameters(parameters);
        request1.executeAsync();

    }

    public void getIndividualdetails(String userid) {

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + userid,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.e("singledataresp", response.toString());
                        JSONObject data = response.getJSONObject();
                        Log.e("singledata", data.toString());
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,address,cover,gender,favorite_teams");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public class getNextorPrevious extends AsyncTask<String, String, String> {
        String data;
        TaggableFreindsFragment caller;

        getNextorPrevious(TaggableFreindsFragment caller) {
            this.caller = caller;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                data = new makeServiceCall().makeServiceGETCall(nextstring);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            caller.onBackgroundTaskCompleted(s);
        }
    }

}
