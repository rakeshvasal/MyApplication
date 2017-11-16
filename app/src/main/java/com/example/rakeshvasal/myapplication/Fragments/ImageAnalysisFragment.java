package com.example.rakeshvasal.myapplication.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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



import java.util.ArrayList;
import java.util.List;

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
public class ImageAnalysisFragment extends BaseFragment{

    FirebaseStorage storage;
    StorageReference storageRef;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;
    String[] array_image_paths;
   ClarifaiClient client;
    String output;
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


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("ImageList");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        FetchAllImageDetails();

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            new StudyImage().execute();

            }
        });



        return root;
    }

    private void FetchAllImageDetails() {
        showProgressDialog();


        try {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    array_image_paths = new String[(int)dataSnapshot.getChildrenCount()];

                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {

                        Image_Items image_items = eventsnapshot.getValue(Image_Items.class);
                        array_image_paths[0] = image_items.getDownload_url();


                    }
                    closeProgressDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    closeProgressDialog();
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                    shortToast("Exception");
                }
            });


        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }
    }





    class StudyImage extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


            final PredictRequest<Concept> predictionResults =
                    client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get your custom models
                            .predict()
                            .withInputs(
                                    ClarifaiInput.forImage("image url here"));

            List<ClarifaiOutput<Concept>> result = predictionResults.executeSync().get();
            result.get(0);
            Log.d("Result",""+result);
            String json = new Gson().toJson(result);
            Log.d("Resultinjson",""+result);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            closeProgressDialog();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }
    }
}
