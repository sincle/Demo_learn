package com.haieros.aidl_binder.custom;

import android.os.IInterface;
import android.os.RemoteException;

import com.haieros.aidl_binder.Book;

import java.util.List;

/**
 * Created by Kang on 2018/1/15.
 */

public interface IBookManager extends IInterface{

    static final java.lang.String DESCRIPTOR = "com.haieros.com.haieros.aidl_client.aidl_binder.custom.IBookManager";
    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    List<Book> getBookList() throws RemoteException;
    void addBook(Book book) throws RemoteException;
}
