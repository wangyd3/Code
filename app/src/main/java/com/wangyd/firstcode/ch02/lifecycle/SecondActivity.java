package com.wangyd.firstcode.ch02.lifecycle;

import android.os.Bundle;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("second title");
    }
}
