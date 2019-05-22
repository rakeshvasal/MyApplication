package com.example.rakeshvasal.myapplication.Activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rakeshvasal.myapplication.Fragments.HomeFragment;
import com.example.rakeshvasal.myapplication.Fragments.OpenSourceCodeFragment;
import com.example.rakeshvasal.myapplication.R;

public class OddessyMain extends BaseActivity {


    FragmentManager fm = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oddessy_main);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void init(){



        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new HomeFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.code:
                try {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle arg = new Bundle();
                    //arg.putString("file_type", "java");
                    arg.putString("file_name","OddessyMain");
                    OpenSourceCodeFragment openSourceCodeFragment= new OpenSourceCodeFragment();
                    openSourceCodeFragment.setArguments(arg);
                    openSourceCodeFragment.show(fm,"SourceCode");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
