package com.wangyd.firstcode;

import android.content.Intent;
import android.os.Bundle;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.menu.MenuActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}

