package com.wangyd.firstcode.ch08.notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wangyd.firstcode.R;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setTitle("Hello World!!");
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        manager.cancel(1);
    }

}
