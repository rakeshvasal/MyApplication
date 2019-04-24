package com.example.rakeshvasal.myapplication.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.MyNewWorkerThread;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Services.FetchIntentService;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MultiThreadingTestActiivity extends AppCompatActivity implements MyNewWorkerThread.Callback {

    TextView textView;
    Button button;
    EditText editText;
    MyReceiver myReceiver;
    private Handler mUiHandler = new Handler();
    private MyWorkerThread mWorkerThread;
    private static boolean isVisible;
    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    private LinearLayout mLeftSideLayout;
    private LinearLayout mRightSideLayout;
    private MyNewWorkerThread mNewWorkerThread;

    public static final String FILTER_ACTION_KEY = "any_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_multi_threading_test);
        setContentView(R.layout.main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.inputText);

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Intent intent = new Intent(MultiThreadingTestActiivity.this, FetchIntentService.class);
                intent.putExtra("message", message);
                //startService(intent);
            }
        });*/
        ////


        isVisible = true;
        mLeftSideLayout = (LinearLayout) findViewById(R.id.leftSideLayout);
        mRightSideLayout = (LinearLayout) findViewById(R.id.rightSideLayout);
        String[] urls = new String[]{"http://developer.android.com/design/media/principles_delight.png",
                "http://developer.android.com/design/media/principles_real_objects.png",
                "http://developer.android.com/design/media/principles_make_it_mine.png",
                "http://developer.android.com/design/media/principles_get_to_know_me.png"};
        mNewWorkerThread = new MyNewWorkerThread(new Handler(), this);
        mNewWorkerThread.start();
        mNewWorkerThread.prepareHandler();
        Random random = new Random();
        for (String url : urls){
            mNewWorkerThread.queueTask(url, random.nextInt(2), new ImageView(this));
        }

        ////

       /* mWorkerThread = new MyWorkerThread("myWorkerThread");
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == 5) {
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MultiThreadingTestActiivity.this,
                                        "I am at the middle of background task",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                }
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MultiThreadingTestActiivity.this,
                                "Background task is completed",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        };
        mWorkerThread.start();
        mWorkerThread.prepareHandler();
        mWorkerThread.postTask(task);
        mWorkerThread.postTask(task);*/


    }

    private void setReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        setReceiver();
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    protected void onPause() {
        isVisible = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mNewWorkerThread.quit();
        super.onDestroy();
    }

    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap, int side) {
        imageView.setImageBitmap(bitmap);
        if (isVisible && side == LEFT_SIDE){
            mLeftSideLayout.addView(imageView);
        } else if (isVisible && side == RIGHT_SIDE){
            mRightSideLayout.addView(imageView);
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("broadcastData");
            textView.setText(textView.getText() + "\n" + message);
        }
    }

    public class MyWorkerThread extends HandlerThread {

        private Handler mWorkerHandler;

        public MyWorkerThread(String name) {
            super(name);
        }

        public void postTask(Runnable task){
            mWorkerHandler.post(task);
        }

        public void prepareHandler(){
            mWorkerHandler = new Handler(getLooper());
        }
    }

    /*@Override
    protected void onDestroy() {
        mWorkerThread.quit();
        super.onDestroy();
    }*/
}

