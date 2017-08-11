package com.wangyd.firstcode.ch10.service;

import android.app.IntentService;
import android.content.Intent;

import com.wangyd.firstcode.Utils.DBG;

public class MyIntentService extends IntentService {

    public static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) { /**好处就是自动在子线程运行并且运行结束后自动退出*/
        DBG.log(TAG, DBG._FUNC_() + " id=" + Thread.currentThread().getId());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DBG.log(TAG, DBG._FUNC_() + " id=" + Thread.currentThread().getId());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DBG.log(TAG, DBG._FUNC_());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DBG.log(TAG, DBG._FUNC_());
        return super.onStartCommand(intent, flags, startId);
    }
}
