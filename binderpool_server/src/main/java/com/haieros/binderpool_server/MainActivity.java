package com.haieros.binderpool_server;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "Thread:"+Thread.currentThread().getName()+"id:"+Thread.currentThread().getId());

        Button kang_click = (Button) findViewById(R.id.kang_click);
        final BinderPool binderPool = BinderPool.getsInstance(MainActivity.this);
        kang_click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                IBinder computeBinder = binderPool.queryBinder(1);

                ICompute compute = ComputerImpl.asInterface(computeBinder);
                Log.e(TAG, "Thread:"+Thread.currentThread().getName()+"id:"+Thread.currentThread().getId());
                try {
                    int result = compute.add(1, 2);
                    Log.e("aaa", "1+2=" + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
