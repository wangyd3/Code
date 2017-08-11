package com.wangyd.firstcode.ch02.lifecycle;

import android.os.Bundle;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;

public class ThirdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("third title");
    }
}
