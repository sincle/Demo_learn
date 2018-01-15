// IBookManager.aidl
package com.haieros.aidl_binder;
import com.haieros.aidl_binder.Book;
import com.haieros.aidl_binder.IOnNewBookArriveListener;
// Declare any non-default types here with import statements

interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
   void registerListener(IOnNewBookArriveListener listener);
   void unregisterListener(IOnNewBookArriveListener listener);
}
