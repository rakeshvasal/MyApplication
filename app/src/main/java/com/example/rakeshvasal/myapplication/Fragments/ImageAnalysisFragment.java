package com.example.rakeshvasal.myapplication.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Activity.Image_Capture_Location;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.Image_Adapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Image_Items;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONAdapterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageAnalysisFragment extends BaseFragment {

    FirebaseStorage storage;
    StorageReference storageRef;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;
    String[] array_image_paths;
    ClarifaiClient client;
    String output, image_path, resultjson;
    LinearLayout parent;

    public ImageAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_image_analysis, container, false);

        client = new ClarifaiBuilder(getResources().getString(R.string.Clairify_API_KEY)).buildSync();
        Button scan = (Button) root.findViewById(R.id.scan);
         parent = (LinearLayout) root.findViewById(R.id.parent);
        image_path = getArguments().getString("image_path");
        Log.d("image_path", "" + image_path);
        if (image_path != null && !image_path.equalsIgnoreCase("")) {
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new StudyImage().execute();

                }
            });
        }


        return root;
    }


    class StudyImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            final PredictRequest<Concept> predictionResults =
                    client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get your custom models
                            .predict()
                            .withInputs(
                                    ClarifaiInput.forImage(image_path));

            List<ClarifaiOutput<Concept>> result = predictionResults.executeSync().get();

            result.get(0);
            Log.d("Result", "" + result);
            resultjson = new Gson().toJson(result);
            Log.d("Resultinjson", "" + resultjson);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            closeProgressDialog();
            showResults();
        }
    }

    private void showResults() {
        HashMap<String,Double> hmap = new HashMap<String,Double>();
        if (resultjson != null) {
            if (!resultjson.equalsIgnoreCase("")) {
                try {
                    JSONArray jsonArray = new JSONArray(resultjson);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                    for (int i =0;i < jsonArray1.length();i++){
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                            String key = jsonObject1.getString("name");
                            Double value = jsonObject1.getDouble("value");
                            hmap.put(key,value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(getActivity());
            tv.setLayoutParams(lparams);
            Double value = Double.parseDouble(mentry.getValue().toString());
            tv.setText(mentry.getKey()+ " = " +value*100);
            this.parent.addView(tv);

        }
    }
}
