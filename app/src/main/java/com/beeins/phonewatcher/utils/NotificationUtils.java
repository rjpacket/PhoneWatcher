package com.beeins.phonewatcher.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.beeins.phonewatcher.R;

public class NotificationUtils {

    public static final String CAHNNEL_ID = "audio_service";

    public static void startForeground(Service service, String channelId, int id) {
        if(Build.VERSION.SDK_INT < 26){
            service.startForeground(id, new Notification());
        }else {
            Notification.Builder builder = new Notification.Builder(service, channelId);
            Notification notification = builder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setContentTitle("助手正在运行...")
                    .build();
            service.startForeground(id, notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static String createChannelId(Context context, String channelId, String channelName, String description) {
//        String channelId = "audio_service";
//        String channelName = "重要";
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setDescription(description);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        return channelId;
    }
}
