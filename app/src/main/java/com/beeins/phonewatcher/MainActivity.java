package com.beeins.phonewatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.beeins.phonewatcher.utils.NotificationUtils;

import static com.beeins.phonewatcher.utils.NotificationUtils.CAHNNEL_ID;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationUtils.createChannelId(this, CAHNNEL_ID, "重要", "1_2_3");

//        startService(new Intent(this, LocalService.class));
//        startService(new Intent(this, RemoteService.class));
//
//        MyJobService.startJob(this);

        startService(new Intent(this,ProcessService.class));
    }
}
