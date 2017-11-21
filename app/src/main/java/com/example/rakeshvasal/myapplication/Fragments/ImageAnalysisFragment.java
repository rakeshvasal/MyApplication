package com.example.rakeshvasal.myapplication.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.rakeshvasal.myapplication.Activity.CharacterRecognition;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONObject;

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
    RecyclerView recyclerView;
    ArrayAdapter<String> baseAdapter;
    ListView lv;

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
        Button ocr = (Button) root.findViewById(R.id.ocr);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        lv = (ListView) root.findViewById(R.id.lv);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        parent = (LinearLayout) root.findViewById(R.id.parent);
        image_path = getArguments().getString("image_path");
        Log.d("image_path", "" + image_path);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image_path != null && !image_path.equalsIgnoreCase("")) {
                    new StudyImage().execute();
                }
            }
        });

        ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CharacterRecognition(getActivity());
            }
        });
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
        HashMap<String, Double> hmap = new HashMap<String, Double>();
        if (resultjson != null) {
            if (!resultjson.equalsIgnoreCase("")) {
                try {
                    JSONArray jsonArray = new JSONArray(resultjson);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        String key = jsonObject1.getString("name");
                        Double value = jsonObject1.getDouble("value");
                        hmap.put(key, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Set<Map.Entry<String, Double>> set = hmap.entrySet();
        String[] content = new String[hmap.size()];
        Iterator<Map.Entry<String, Double>> iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {

            Map.Entry<String, Double> mentry = iterator.next();
            String key = mentry.getKey().toString();
            Double value = Double.parseDouble(mentry.getValue().toString());
            Log.d("value " + key, " :" + value);
            Double s = value * 100;
            Log.d("s " + key, " :" + s);
            double roundOff = Math.round(s * 100.0) / 100.0;
            content[i] = key + " : " + roundOff + "%";
            i++;
        }

        baseAdapter = new ArrayAdapter<>(getActivity(), R.layout.memo_content, R.id.memo_list_items, content);
        //recyclerView.setAdapter();
        lv.setAdapter(baseAdapter);
        baseAdapter.notifyDataSetChanged();
    }
}
