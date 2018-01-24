// ISecurityCenter.aidl
package com.haieros.binderpool_server;

// Declare any non-default types here with import statements

interface ISecurityCenter {

    String encrypt(in String content);
    String decrypt(in String password);
}
