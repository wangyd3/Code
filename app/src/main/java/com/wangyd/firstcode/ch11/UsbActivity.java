package com.wangyd.firstcode.ch11;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.partition.Partition;
import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.Utils.DBG;
import com.wangyd.firstcode.R;

public class UsbActivity extends BaseActivity {

    private Activity aty = this;
    private static final String TAG = "UsbActivity";
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);
        tv = (TextView) findViewById(R.id.info_dev);
        addLog("start");
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
        setResult(RESULT_CANCELED);

        getUsbMessage();

//        UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(this);//获取存储设备
//
//        for (UsbMassStorageDevice device : storageDevices) {//可能有几个 一般只有一个 因为大部分手机只有1个otg插口
//            read(device);
//        }
    }

    UsbManager usbManager;
    UsbMassStorageDevice[] storageDevices;
    int index;
    PendingIntent pendingIntent;

    void getUsbMessage() {

        storageDevices = UsbMassStorageDevice.getMassStorageDevices(this);//获取存储设备
        addLog("storageDevices = " + storageDevices.length);
        if (storageDevices.length == 2)
            setResult(RESULT_OK);

        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        for (int i = 0; i < storageDevices.length; i++) {
            if (usbManager.hasPermission(storageDevices[i].getUsbDevice())) {//有就直接读取设备是否有权限
                read(storageDevices[i]);
            } else {//没有就去发起意图申请
                usbManager.requestPermission(storageDevices[i].getUsbDevice(), pendingIntent); //该代码执行后，系统弹出一个对话框，
                index = i;
                break;
            }
        }
    }

//    void getUsbMessage() {
//        storageDevices = UsbMassStorageDevice.getMassStorageDevices(this);//获取存储设备
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
//        for (UsbMassStorageDevice device : storageDevices) {//可能有几个 一般只有一个 因为大部分手机只有1个otg插口
//            if (usbManager.hasPermission(device.getUsbDevice())) {//有就直接读取设备是否有权限
//                read(device);
//            } else {//没有就去发起意图申请
//                usbManager.requestPermission(device.getUsbDevice(), pendingIntent); //该代码执行后，系统弹出一个对话框，
//            }
//        }
//    }


    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USB_PERMISSION://接受到自定义广播
                    synchronized (this) {
                        UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {  //允许权限申请
                            if (usbDevice != null) {
                                addLog("用户授权成功");
                                for (int i = index; i < storageDevices.length; i++) {
                                    if (usbManager.hasPermission(storageDevices[i].getUsbDevice())) {//有就直接读取设备是否有权限
                                        read(storageDevices[i]);
                                    } else {//没有就去发起意图申请
                                        usbManager.requestPermission(storageDevices[i].getUsbDevice(), pendingIntent); //该代码执行后，系统弹出一个对话框，
                                        index = i;
                                        break;
                                    }
                                }
                            }
                        } else {
                            aty.setResult(RESULT_CANCELED);
                            addLog("用户未授权，读取失败");
                        }
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_ATTACHED://接收到存储设备插入广播
                    UsbDevice device_add = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device_add != null) {
                        addLog("接收到存储设备插入广播");
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED://接收到存储设备拔出广播
                    UsbDevice device_remove = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device_remove != null) {
                        addLog("接收到存储设备拔出广播");
                    }
                    break;
            }
        }
    };


    private void read(UsbMassStorageDevice massDevice) {
        // before interacting with a device you need to call init()!
        try {
            massDevice.init();//初始化
            // Only uses the first partition on the device
            Partition partition = massDevice.getPartitions().get(0);
            FileSystem currentFs = partition.getFileSystem();
            //fileSystem.getVolumeLabel()可以获取到设备的标识
            //通过FileSystem可以获取当前U盘的一些存储信息，包括剩余空间大小，容量等等
            addLog("总容量: " + (float) currentFs.getCapacity() / 1024 / 1024 / 1024 + "GB");
            addLog("已用空间: " + (float) currentFs.getOccupiedSpace() / 1024 / 1024 / 1024 + "GB");
            addLog("剩余空间: " + (float) currentFs.getFreeSpace() / 1024 / 1024 / 1024 + "GB");
            //addLog("Chunk size: " + currentFs.getChunkSize() + "bytes");
            addLog("\n");
            UsbFile root = currentFs.getRootDirectory();
        } catch (Exception e) {
            DBG.log(TAG, "read:", e);
            setResult(RESULT_CANCELED);
            addLog("读取失败");
        }
    }


    void addLog(String s) {
        tv.append(s + "\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUsbReceiver);
    }
}
