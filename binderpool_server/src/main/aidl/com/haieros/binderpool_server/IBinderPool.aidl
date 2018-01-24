// IBinderPool.aidl
package com.haieros.binderpool_server;

// Declare any non-default types here with import statements

interface IBinderPool {
   IBinder queryBinder(int binderCode);
}
