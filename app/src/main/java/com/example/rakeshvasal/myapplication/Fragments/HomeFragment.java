package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments.AddUpdateUserFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Axisvation on 9/8/2017.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    boolean match = false;
    private DatabaseReference mUserDatabase, userref, ref, childref;
    FirebaseDatabase mFirebaseInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);
        mTitle = mDrawerTitle = getActivity().getTitle();
        mDrawerLayout = (DrawerLayout) root.findViewById(R.id.drawer_layout);

        // set a custom shadow that overlays the main content when the drawer opens
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mUserDatabase = mFirebaseInstance.getReference();
        userref = mUserDatabase.child("users");
        //et_email.setText("rakeshvasal@gmail.com");
        //et_password.setText("pass");
        et_email.setText("sachin.tendulkar@gmail.com");
        et_password.setText("pass@123");
        operations();
        return root;
    }

    private void operations() {

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                arg.putString("task", "Add");
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new AddUpdateUserFragment();
                fragment.setArguments(arg);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = et_email.getText().toString();
                String pass = et_password.getText().toString();
                validateUser(emailid, pass);

            }
        });
    }

    private void validateUser(final String email_id, final String password) {


        try {
            userref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Log.d("eventsnapshot", "" + eventsnapshot);
                        User user = eventsnapshot.getValue(User.class);

                        String user_email = user.getUser_email();
                        if (user_email.equalsIgnoreCase(email_id)) {
                            if (password.equalsIgnoreCase(user.getPassword())) {
                                String role = user.getRole();
                                String user_id = user.getUser_id();
                                Log.d("user_id", "" + user_id);
                                if (role.equalsIgnoreCase("Admin")) {
                                    match = true;
                                    Bundle arg = new Bundle();
                                    arg.putString("user_id", user_id);
                                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                                    Fragment fragment = new FestAdminDashboard();
                                    fragment.setArguments(arg);
                                    transaction.replace(R.id.fragment_container, fragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    break;
                                } else {
                                    match = true;
                                    Bundle arg = new Bundle();
                                    arg.putString("user_id", user_id);
                                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                                    Fragment fragment = new FestUserDashboard();
                                    fragment.setArguments(arg);
                                    transaction.replace(R.id.fragment_container, fragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    break;
                                }
                            } else {
                                longToast("Passwords Dont Match");
                                return;
                            }
                        }
                    }
                    if (!match) {
                        longToast("User doesnot exist..Please check Email Id");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    closeProgressDialog();
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                }
            });


        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }

/*
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        Fragment fragment = new FestAdminDashboard();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/

    }

}
