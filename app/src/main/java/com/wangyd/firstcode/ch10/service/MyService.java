package com.wangyd.firstcode.ch10.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.DBG;

public class MyService extends Service {

    private static final String TAG = "MyService";
    private DownloadBinder mBinder = new DownloadBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        DBG.log(TAG, DBG._FUNC_());
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        DBG.log(TAG, DBG._FUNC_());

        Intent intent = new Intent(this, ServiceActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is a content title")
                .setContentText("this is a content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DBG.log(TAG, DBG._FUNC_());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        DBG.log(TAG, DBG._FUNC_());
        super.onDestroy();
    }


    class DownloadBinder extends Binder {
        public void startDownload() {
            DBG.log(TAG, DBG._FUNC_());
        }

        public int getProgress() {
            DBG.log(TAG, DBG._FUNC_());
            return 0;
        }

    }


}

