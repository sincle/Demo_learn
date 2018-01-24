package com.haieros.binderpool_server;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by Kang on 2018/1/16.
 */

public class BinderPoolImpl extends IBinderPool.Stub {
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
}
