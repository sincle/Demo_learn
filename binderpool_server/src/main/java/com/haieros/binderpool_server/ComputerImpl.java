package com.haieros.binderpool_server;

import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Kang on 2018/1/16.
 */

public class ComputerImpl extends ICompute.Stub {
    private static final String TAG = ComputerImpl.class.getSimpleName();
    @Override
    public int add(int a, int b) throws RemoteException {
        Log.e(TAG, "thread:"+Thread.currentThread().getId()+",name:"+Thread.currentThread().getName());
        return a + b;
    }
}
