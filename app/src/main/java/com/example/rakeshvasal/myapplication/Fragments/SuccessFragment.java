package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.R;


public class SuccessFragment extends BaseFragment {

    TextView message;
    Button submit;

    String trans_id;
    String[] events;
    int amount;

    public SuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_success, container, false);


        message = (TextView) root.findViewById(R.id.message);
        submit = (Button) root.findViewById(R.id.submit);
        trans_id = getArguments().getString("trans_id");
        amount = getArguments().getInt("amt");
        events = getArguments().getStringArray("eventarray");
        String strevents = "";

        for (int i = 0; i < events.length; i++) {
            strevents = strevents + "\n" + events[i];
        }

        message.setText("Success\n You have successfully Registered for the Following Events :\n" + strevents + ".\nTotal Amount Paid is :" + amount);

        // Inflate the layout for this fragment
        return root;
    }

}
