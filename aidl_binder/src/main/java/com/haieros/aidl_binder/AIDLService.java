package com.haieros.aidl_binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AIDLService extends Service {

    private static final String TAG = AIDLService.class.getSimpleName();
    //包含Book对象的list
    private List<Book> mBooks = new ArrayList<>();
    private CopyOnWriteArrayList<IOnNewBookArriveListener> mListeners = new CopyOnWriteArrayList<>();
    private IBinder mManagerService = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (this) {
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks != null) {
                    return mBooks;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                if (!mBooks.contains(book)) {
                    Log.e(TAG, "add book successfully");
                    onNewBookArrived(book);
                    //mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }

        @Override
        public void registerListener(IOnNewBookArriveListener listener) throws RemoteException {
            if(!mListeners.contains(listener)) {
                mListeners.add(listener);
            }else {
                Log.e(TAG, "listener is exist");
            }
            Log.e(TAG, "listener size is "+mListeners.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArriveListener listener) throws RemoteException {

            if(mListeners.contains(listener)) {
                mListeners.remove(listener);
            }
            else {
                Log.e(TAG, "listener is not found");
            }
            Log.e(TAG, "listener size is "+mListeners.size());
        }
    };

    public AIDLService() {
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBooks.add(book);
        for (int i = 0; i < mListeners.size(); i++) {
            Log.e(TAG, "dispath listener");
            mListeners.get(i).onNewBookArrived(book);
        }

    }
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
        Book book = new Book();
        book.setBookID(1);
        book.setBookName("andorid");
        mBooks.add(book);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return mManagerService;
    }
}
