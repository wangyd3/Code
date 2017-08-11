package com.wangyd.firstcode.ch08.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;

public class NotificationActivity extends BaseActivity {
    NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Button send = (Button) findViewById(R.id.sendNotification);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            done(i);
                        }
                    }
                }).start();
            }
        });
    }


    void done(int i) {
        Intent intent = new Intent(NotificationActivity.this, ShowActivity.class);
        PendingIntent pi = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(NotificationActivity.this)
                .setContentText("this is—is " + i)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                //.setLights(Color.RED, 1000, 1000)
                .setVibrate(new long[]{500, 500, 500, 500})//要调成振动模式才有
                // .setAutoCancel(true)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("Learn" + i))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                //.setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .build();//不要少了个build()函数
        manager.notify(1, notification);
    }

}
