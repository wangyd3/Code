package com.wangyd.firstcode.zz;

import android.content.Context;
import android.os.Bundle;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.widget.TextView;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.Utils.DBG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileActivity extends BaseActivity {
    private final String TAG = "FileActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        textView = (TextView) findViewById(R.id.log);
        display("file test");


        getStorages();
    }


    private void display(String s, Object... args) {
        String str = String.format(Locale.US, s, args);
        textView.append(str + "\n");
    }


    //通过 android:sharedUserId="android.uid.system" 能够正常的读取
    private boolean rdwr(String path) {
        File file = new File(path + "/test.txt");
        String str = "this is a test about read and write on memery card";
        FileOutputStream fileOS;
        try {
            fileOS = new FileOutputStream(file);
            fileOS.write(str.getBytes());
            fileOS.close();
        } catch (FileNotFoundException e) {
            DBG.log(TAG, "FileNotFoundException" + "i" + ": ", e);
        } catch (IOException e) {
            DBG.log(TAG, "IOException" + "i" + ": ", e);
        }

        byte[] buffer = new byte[128];
        FileInputStream fileIS;
        int len = 0;
        try {
            fileIS = new FileInputStream(file);
            len = fileIS.read(buffer, 0, 100);
            display("len=%d", len);
            display(new String(buffer, 0, len));
            fileIS.close();
        } catch (FileNotFoundException e) {
            DBG.log(TAG, "FileNotFoundException" + "i" + ": ", e);
        } catch (IOException e) {
            DBG.log(TAG, "IOException" + "i" + ": ", e);
        }

        file.delete();

        boolean ret = str.equals(new String(buffer, 0, len));
        if (ret)
            display("文件读写成功");
        else
            display("文件读写失败");

        return ret;
    }


    /**
     * A920一般有三个,前面两个是SD,第三个是USB
     * D800一般有三个,前面两个是SD,第三个是USB
     * A920C一般有五个,前面两个是SD,最后一个才是USB
     * PX7的没有区分。第一个是SD,然后SD和USB是一样的。
     */
    @SuppressWarnings("deprecation")
    private List<Storage> getStorages() {
        StorageManager sm = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        List<Storage> storages = new ArrayList<>();

        try {
            String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths").invoke(sm);
            for (int i = 0; i < paths.length; i++) {
                Storage storage = new Storage(paths[i]);
                String state = (String) sm.getClass().getMethod("getVolumeState", String.class).invoke(sm, paths[i]);

                storage.state = state;
                if (state.equals("mounted")) {
                    File path = new File(storage.path);
                    StatFs statFs = new StatFs(path.getPath());

                    long blocksize = statFs.getBlockSize();
                    long totalblocks = statFs.getBlockCount();
                    long availableblocks = statFs.getAvailableBlocks();

                    long totalsize = blocksize * totalblocks;
                    long availablesize = availableblocks * blocksize;

                    storage.total = android.text.format.Formatter.formatFileSize(this, totalsize);
                    storage.available = android.text.format.Formatter.formatFileSize(this, availablesize);

                    rdwr(storage.path);

                }
                storages.add(storage);
            }
        } catch (Exception e) {
            DBG.log(TAG, "getStorages:", e);
        }

        DBG.log(TAG, "storages.size = " + storages.size());
        //display("storages.size = " + storages.size());
        for (int i = 0; i < storages.size(); i++) {
            Storage s = storages.get(i);
            DBG.log(TAG, "stoarge" + i + "=" + s.path + "," + s.state);
            DBG.log(TAG, "available/total=%s/%s", s.available, s.total);
            //display("stoarge" + i + "=" + s.path + "," + s.state);
            //display("available/total=" + s.available + "/" + s.total);
        }

        return storages;
    }


    class Storage {
        public String total = "null";
        public String available = "null";
        public String path;
        public String state;

        public Storage(String total, String available, String path, String state) {
            this.total = total;
            this.available = available;
            this.path = path;
            this.state = state;
        }

        public Storage(String path) {
            this.path = path;
        }

        public Storage() {

        }

    }


}
