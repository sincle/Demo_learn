package com.haieros.messenger_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.haieros.messager_client.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Messenger mService;
    private static final int MSG_FROM_CLIENT = 0x01;
    private static final int MMSG_FROM_SERVER = 0x02;
    private static Messenger messenger = new Messenger(new MessengerHandler());
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MMSG_FROM_SERVER:
                    Log.e(TAG, "form server:"+msg.getData().getString("msg"));

                    break;
            }
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            mService = new Messenger(service);
            Message message = Message.obtain(null, MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "from client");
            message.setData(bundle);
            message.replyTo = messenger;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button kang_click = (Button) findViewById(R.id.kang_click);

        kang_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.haieros.messenger_server", "com.haieros.messenger_server.MessengerService");
                //intent.setPackage("com.haieros.messenger_client");
                bindService(intent, conn, BIND_AUTO_CREATE);
            }
        });
    }
}
