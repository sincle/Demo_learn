package com.haieros.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.haieros.aidl_binder.Book;
import com.haieros.aidl_binder.IBookManager;
import com.haieros.aidl_binder.IOnNewBookArriveListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private IBookManager mBookManager = null;

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    private IOnNewBookArriveListener.Stub listener = new IOnNewBookArriveListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.e(TAG, "newbook:"+newBook.toString() );
        }
    };
    //包含Book对象的list
    private List<Book> mBooks;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");

            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                mBookManager.registerListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBookList();
                    Log.e(TAG, mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
            mBound = false;
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
                if (!mBound) {
                    attemptToBindService();
                    Toast.makeText(MainActivity.this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mBookManager == null) return;

                Book book = new Book();
                book.setBookID(2);
                book.setBookName("2345");
                try {
                    mBookManager.addBook(book);
                    Log.e(TAG, book.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setClassName("com.haieros.aidl_binder", "com.haieros.aidl_binder.AIDLService");
        //intent.setAction("com.haieros.aidl");
        intent.setPackage("com.haieros.aidl_client");
        this.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
}
