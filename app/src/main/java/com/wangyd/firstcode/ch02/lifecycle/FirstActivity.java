package com.wangyd.firstcode.ch02.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;

public class FirstActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button secondBtn = (Button) findViewById(R.id.secondBtn);
        Button thirdBtn = (Button) findViewById(R.id.thirdBtn);

        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));

            }
        });

        thirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, ThirdActivity.class));
            }
        });
    }

}
