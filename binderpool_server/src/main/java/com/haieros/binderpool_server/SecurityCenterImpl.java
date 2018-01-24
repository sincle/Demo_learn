package com.haieros.binderpool_server;

import android.os.RemoteException;

/**
 * Created by Kang on 2018/1/16.
 */

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] + 1);
        }

        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        char[] chars = password.toCharArray();
        for (int i=0; i<chars.length;i++) {
            chars[i] = (char) (chars[i]-1);
        }

        return new String(chars);
    }
}
