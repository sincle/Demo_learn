// IOnNewBookArriveListener.aidl
package com.haieros.aidl_binder;
import com.haieros.aidl_binder.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArriveListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book newBook);
}
