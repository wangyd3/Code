package com.wangyd.firstcode.ch02.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.BaseActivity;

public class IntentActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        findViewById(R.id.startIntentBtn).setOnClickListener(this);
        findViewById(R.id.startDialBtn).setOnClickListener(this);
        findViewById(R.id.startWebBtn).setOnClickListener(this);
    }

    /**
     * <data></data>标签
     *
     *
     * */


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.startIntentBtn:
                intent.setAction("com.wangyd.firstcode.INTENT_TEST");
                //intent.addCategory("android.intent.category.MY_DEFAULT");/** Manifest里面要找到才行*/
                startActivity(intent);
                break;
            case R.id.startDialBtn:
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1008611"));
                startActivity(intent);
                break;
            case R.id.startWebBtn:
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.baidu.com/"));
                startActivity(intent);
                break;
        }
    }
}
