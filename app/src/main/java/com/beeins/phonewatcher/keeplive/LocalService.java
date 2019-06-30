package com.beeins.phonewatcher.keeplive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.beeins.phonewatcher.utils.NotificationUtils;

import static com.beeins.phonewatcher.utils.NotificationUtils.CAHNNEL_ID;

public class LocalService extends Service {

    private static final String TAG = "Service";
    private KeepLiveBinder myBinder;

    private ServiceConnection serviceConnection;
    private static final int NOTIFICATION_ID = 1001;

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myBinder = new KeepLiveBinder();
        serviceConnection = new ServiceConnection();
        //让服务变成前台服务
        NotificationUtils.startForeground(this, CAHNNEL_ID, NOTIFICATION_ID);

        //如果 API 18 以上的设备 启动一个相同的 id 的 Service startForeground
        //然后结束这个 Service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startService(new Intent(this, InnerService.class));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this, RemoteService.class), serviceConnection, BIND_AUTO_CREATE);

        return super.onStartCommand(intent, flags, startId);
    }

    public static class InnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            NotificationUtils.startForeground(this, CAHNNEL_ID, NOTIFICATION_ID);
            stopSelf();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    class ServiceConnection implements android.content.ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务连接后回调
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "Remote进程可能被干掉了，拉活");
            //连接中断后回调
            startService(new Intent(LocalService.this, RemoteService.class));
            bindService(new Intent(LocalService.this, RemoteService.class), serviceConnection, BIND_AUTO_CREATE);
        }
    }
}