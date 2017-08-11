package com.wangyd.firstcode.ch03.customview;

import android.os.Bundle;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;

public class CustomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }

    }
}
