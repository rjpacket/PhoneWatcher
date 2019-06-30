package com.beeins.phonewatcher;

public class Watcher {
    static {
        System.loadLibrary("native-lib");
    }

    public native void createWatcher(String userId);

    public native void connectMonitor();
}
