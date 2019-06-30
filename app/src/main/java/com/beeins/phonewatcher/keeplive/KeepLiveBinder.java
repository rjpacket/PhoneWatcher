package com.beeins.phonewatcher.keeplive;

import android.os.RemoteException;

import com.beeins.phonewatcher.IKeepLiveAidlInterface;

public class KeepLiveBinder extends IKeepLiveAidlInterface.Stub {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
}
