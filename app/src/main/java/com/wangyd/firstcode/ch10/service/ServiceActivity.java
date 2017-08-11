package com.wangyd.firstcode.ch10.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.Utils.DBG;

/**
 * 关于handler、message、MessageQueue、looper、AsyncTask的简单说明
 * 1、一个线程Thread只有一个Looper,这个Looper不是默认有的,需要通过调用Looper.prepare();
 * 和Looper.loop();启动和初始化,Activity默认是有一个Looper,所以不需要调用prepare和loop
 * <p>
 * 2、handler会绑定一个线程,在那个线程里面实例化则绑定那个线程,handler是用来发送和接受消息的
 * handler可以发送和接受多个消息,Looper是管理handler message机制的
 * <p>
 * 3、message是通过handler来操作的,可以handler主动来发送,也可以是message设置handler来发送
 * 获取message的最好办法是Message.obtain()或者Handler.obtainMessage()
 * <p>
 * 4、post和sendMessage 在handler里面覆盖sendMessage方法来处理message
 * post里面的runable是跟handler实现的位置是同一个线程
 * <p>
 * 5、AsyncTask AsyncTask<String... params(参数), Integer... progress(进度), Integer...result(结果)>
 * new AsyncTask().execute();启动
 * <p>
 * <p>
 * <p>
 * 一、 Service简介
 * Service是android 系统中的四大组件之一（Activity、Service、BroadcastReceiver、ContentProvider）
 * 它跟Activity的级别差不多,但不能自己运行只能后台运行,并且可以和其他组件进行交互。service可以在很多场合的应用中使用,
 * 比如播放多媒体的时候用户启动了其他Activity这个时候程序要在后台继续播放,比如检测SD卡上文件的变化,
 * 再或者在后台记录你地理信息位置的改变等等,总之服务总是藏在后台的。
 * Service的启动有两种方式：context.startService() 和 context.bindService()
 * <p>
 * 二、 Service启动流程
 * context.startService() 启动流程：
 * context.startService()  -> onCreate()  -> onStart()  -> Service running
 * -> context.stopService()  -> onDestroy()  -> Service stop
 * <p>
 * 如果Service还没有运行，则android先调用onCreate()，然后调用onStart()；
 * 如果Service已经运行，则只调用onStart()，所以一个Service的onStart方法可能会重复调用多次。
 * 如果stopService的时候会直接onDestroy，如果是调用者自己直接退出而没有调用stopService的话，
 * Service会一直在后台运行，该Service的调用者再启动起来后可以通过stopService关闭Service。
 * 所以调用startService的生命周期为：onCreate --> onStart (可多次调用) --> onDestroy
 * <p>
 * context.bindService()启动流程：
 * context.bindService()  -> onCreate()  -> onBind()  -> Service running  -> onUnbind()  -> onDestroy()  -> Service stop
 * <p>
 * onBind()将返回给客户端一个IBind接口实例，IBind允许客户端回调服务的方法，比如得到Service的实例、运行状态或其他操作。
 * 这个时候把调用者（Context，例如Activity）会和Service绑定在一起，
 * Context退出了，Srevice就会调用onUnbind->onDestroy相应退出。
 * 所以调用bindService的生命周期为：onCreate --> onBind(只一次，不可多次绑定) --> onUnbind --> onDestory。
 * 在Service每一次的开启关闭过程中，只有onStart可被多次调用(通过多次startService调用)，
 * 其他onCreate，onBind，onUnbind，onDestory在一个生命周期中只能被调用一次。
 * <p>
 * <p>
 * 三、 Service生命周期
 * Service的生命周期并不像Activity那么复杂，它只继承了onCreate()、onStart()、onDestroy()三个方法
 * 当我们第一次启动Service时，先后调用了onCreate()、onStart()这两个方法；当停止Service时，则执行onDestroy()方法。
 * 这里需要注意的是，如果Service已经启动了，当我们再次启动Service时，不会在执行onCreate()方法，而是直接执行onStart()方法。
 * 它可以通过Service.stopSelf()方法或者Service.stopSelfResult()方法来停止自己，只要调用一次stopService()方法便可以停止服务，无论调用了多少次的启动服务方法。
 * <p>
 * 一、 Service简介
 * Service是android 系统中的四大组件之一（Activity、Service、BroadcastReceiver、ContentProvider）
 * 它跟Activity的级别差不多,但不能自己运行只能后台运行,并且可以和其他组件进行交互。service可以在很多场合的应用中使用,
 * 比如播放多媒体的时候用户启动了其他Activity这个时候程序要在后台继续播放,比如检测SD卡上文件的变化,
 * 再或者在后台记录你地理信息位置的改变等等,总之服务总是藏在后台的。
 * Service的启动有两种方式：context.startService() 和 context.bindService()
 * <p>
 * 二、 Service启动流程
 * context.startService() 启动流程：
 * context.startService()  -> onCreate()  -> onStart()  -> Service running
 * -> context.stopService()  -> onDestroy()  -> Service stop
 * <p>
 * 如果Service还没有运行，则android先调用onCreate()，然后调用onStart()；
 * 如果Service已经运行，则只调用onStart()，所以一个Service的onStart方法可能会重复调用多次。
 * 如果stopService的时候会直接onDestroy，如果是调用者自己直接退出而没有调用stopService的话，
 * Service会一直在后台运行，该Service的调用者再启动起来后可以通过stopService关闭Service。
 * 所以调用startService的生命周期为：onCreate --> onStart (可多次调用) --> onDestroy
 * <p>
 * context.bindService()启动流程：
 * context.bindService()  -> onCreate()  -> onBind()  -> Service running  -> onUnbind()  -> onDestroy()  -> Service stop
 * <p>
 * onBind()将返回给客户端一个IBind接口实例，IBind允许客户端回调服务的方法，比如得到Service的实例、运行状态或其他操作。
 * 这个时候把调用者（Context，例如Activity）会和Service绑定在一起，
 * Context退出了，Srevice就会调用onUnbind->onDestroy相应退出。
 * 所以调用bindService的生命周期为：onCreate --> onBind(只一次，不可多次绑定) --> onUnbind --> onDestory。
 * 在Service每一次的开启关闭过程中，只有onStart可被多次调用(通过多次startService调用)，
 * 其他onCreate，onBind，onUnbind，onDestory在一个生命周期中只能被调用一次。
 * <p>
 * <p>
 * 三、 Service生命周期
 * Service的生命周期并不像Activity那么复杂，它只继承了onCreate()、onStart()、onDestroy()三个方法
 * 当我们第一次启动Service时，先后调用了onCreate()、onStart()这两个方法；当停止Service时，则执行onDestroy()方法。
 * 这里需要注意的是，如果Service已经启动了，当我们再次启动Service时，不会在执行onCreate()方法，而是直接执行onStart()方法。
 * 它可以通过Service.stopSelf()方法或者Service.stopSelfResult()方法来停止自己，只要调用一次stopService()方法便可以停止服务，无论调用了多少次的启动服务方法。
 * <p>
 * <p>
 * 实验结果是
 * 1、如果service没有启动过,调用bind后退出Acitivity或者调用unbind都会Destory
 * 2、如果service同个start启动过,只能调用stop,不然不会Destory
 * 3、调用bind后才能调用unbind,不然会出错.
 */


public class ServiceActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ServiceActivity";
    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DBG.log(TAG, DBG._FUNC_());
            downloadBinder = (MyService.DownloadBinder) service; /**这里赋值后可以在activity的其他地方调用*/
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            DBG.log(TAG, DBG._FUNC_());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        findViewById(R.id.startService).setOnClickListener(this);
        findViewById(R.id.stopService).setOnClickListener(this);
        findViewById(R.id.bindService).setOnClickListener(this);
        findViewById(R.id.unbindService).setOnClickListener(this);
        findViewById(R.id.startIntentService).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()) {
            case R.id.startService:
                startService(intent);
                break;
            case R.id.stopService:
                stopService(intent);
                break;
            case R.id.bindService:
                /**connection里面有个回调有Ibinder,这个Ibinder是Service返回的*/
                bindService(intent, connection, BIND_AUTO_CREATE);

                break;
            case R.id.unbindService:
                unbindService(connection);
                break;
//            case R.id.startIntentService:
//                downloadBinder.getProgress();//测试能否正常调用
//                break;
            case R.id.startIntentService:
                DBG.log(TAG, DBG._FUNC_() + " id=" + Thread.currentThread().getId());
                Intent i = new Intent(this, MyIntentService.class);
                startService(i);
                break;
        }
    }
}
