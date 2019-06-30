package com.beeins.phonewatcher;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ProcessService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int index;
    private static final String TAG = "ProcessService";

    @Override
    public void onCreate() {
        super.onCreate();
        Watcher watcher = new Watcher();
        watcher.createWatcher(String.valueOf(Process.myUid()));
        watcher.connectMonitor();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "run: 你杀不死我 " + index);
                index++;
            }
        }, 0, 5000);
    }
}
