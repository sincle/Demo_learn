package com.haieros.binderpool_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class PoolService extends Service {

    private Binder mBinderPool = new IBinderPool.Stub() {
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode) {
                case 0:
                    return new SecurityCenterImpl();
                case 1:
                    return new ComputerImpl();
            }
            return null;
        }
    };

    public PoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinderPool;
    }
}
