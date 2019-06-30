package com.beeins.phonewatcher.keeplive;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    public static void startJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context
                .JOB_SCHEDULER_SERVICE);

        JobInfo.Builder builder = new JobInfo.Builder(10, new ComponentName(context
                .getPackageName(), MyJobService.class
                .getName()));
        // setPersisted 在设备重启依然执行
        builder.setPersisted(true);
        //小于7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // 每隔1s 执行一次 job
            builder.setPeriodic(1_000);
        } else {
            //延迟执行任务
            builder.setMinimumLatency(1_000);
        }

        jobScheduler.schedule(builder.build());
    }

    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "开启job");
        //如果7.0以上,则轮训
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startJob(this);
        }

        //判断服务是否正在运行，没有的话，则调起服务
        boolean isLocalRun = isRunningService(this, LocalService.class.getName());
        boolean isRemoteRun = isRunningService(this, RemoteService.class.getName());
        if (!isLocalRun || !isRemoteRun) {
            startService(new Intent(this, LocalService.class));
            startService(new Intent(this, RemoteService.class));
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    public boolean isRunningService(Context context, String name) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : runningServices
                ) {
            if (TextUtils.equals(info.service.getClassName(), name)) {
                return true;
            }
        }
        return false;
    }
}