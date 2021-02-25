package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends DialogFragment {

    Activity mContext;
    TextView name;
    View root;

    User user;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getActivity();
        String Userjson = "";

        Userjson = getArguments().getString("userjson");
        try {
            JSONArray jsonArray = new JSONArray(Userjson);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Userjson = "" + jsonObject;
            if (Userjson != null) {
                if (!Userjson.equalsIgnoreCase("")) {
                    user = new Gson().fromJson(Userjson, User.class);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LayoutInflater li = LayoutInflater.from(getActivity());
        root = li.inflate(R.layout.fragment_my_profile, null);
        name = (TextView) root.findViewById(R.id.name);
        try {
            if (user != null) {
                name.setText(user.getUser_name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setView(root)
                // Set Dialog Title
                .setTitle("Profile")
                // Set Dialog Message
                //.setMessage("Alert DialogFragment Tutorial")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // mContext.finish();
                    }
                })

                .create();

    }

}
