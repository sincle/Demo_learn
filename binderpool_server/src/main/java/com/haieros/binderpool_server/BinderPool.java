package com.haieros.binderpool_server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Kang on 2018/1/16.
 */

public class BinderPool {

    private static final String TAG = BinderPool.class.getSimpleName();
    private Context mContext;
    // volatile 用来修饰被不同线程访问和修改的变量
    private static volatile BinderPool sInstance;
    /**
     * CountDownLatch类是一个同步计数器,构造时传入int参数,该参数就是计数器的初始值，每调用一次countDown()方法，计数器减1,计数器大于0 时，await()方法会阻塞程序继续执行
     * CountDownLatch如其所写，是一个倒计数的锁存器，当计数减至0时触发特定的事件。利用这种特性，可以让主线程等待子线程的结束。
     */
    private CountDownLatch mConnectBinderPoolCountDownLatch;
    private IBinderPool pool;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    public IBinder queryBinder(int binderCode) {
        try {
            return pool.queryBinder(binderCode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized void connectBinderPoolService() {
        Log.e(TAG, "connectBinderPoolService");
        // 只有一个线程有效
        //mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, PoolService.class);
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        // 等待，直到CountDownLatch中的线程数为0
//        try {
//            mConnectBinderPoolCountDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "------onServiceConnected-------");
            pool = IBinderPool.Stub.asInterface(service);
            try {
                pool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            // 执行一次countDown，其计数减一
//            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            // 解除死亡绑定
            pool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            pool = null;
            // 重连
            connectBinderPoolService();
        }
    };

//    public static class BinderPoolImpl extends IBinderPool.Stub {
//
//        @Override
//        public IBinder queryBinder(int binderCode) throws RemoteException {
//            switch (binderCode) {
//                case BINDER_SECURITY_CENTER:
//                    return new SecurityCenterImpl();
//                case BINDER_COMPUTE:
//                    return new ComputerImpl();
//            }
//            return null;
//        }
//    }
}
