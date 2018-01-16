package com.haieros.messenger_server;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    private static final String TAG = MessengerService.class.getSimpleName();

    private static class MessengerHandler extends Handler {
        private static final int MSG_FROM_CLIENT = 0x01;
        private static final int MMSG_FROM_SERVER = 0x02;

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.e(TAG, "receive msg from client:" + msg.getData().getString("msg"));

                    //回复
                    Messenger client = msg.replyTo;
                    Message clentMsg = Message.obtain(null, MMSG_FROM_SERVER);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg","from server");
                    clentMsg.setData(bundle);
                    try {
                        client.send(clentMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }
}
