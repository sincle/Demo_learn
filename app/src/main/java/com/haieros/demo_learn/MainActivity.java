package com.haieros.demo_learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.haieros.demo_learn.crash.CrashHandler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String text= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashHandler.getInstance().swithCrashActivity(this);
        if (text.length() == 1){
            Log.e(TAG, "-------------------");
        }
    }
}
