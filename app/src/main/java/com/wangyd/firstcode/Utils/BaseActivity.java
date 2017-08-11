package com.wangyd.firstcode.Utils;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
        ActivityCollector.removeActivity(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
    }


    @Override
    protected void onStop() {
        super.onStop();
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
    }


    @Override
    protected void onPause() {
        super.onPause();
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        DBG.log(TAG, DBG._FUNC_() + " " + getClass().getSimpleName());
    }
}
