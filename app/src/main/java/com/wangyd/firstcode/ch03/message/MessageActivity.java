package com.wangyd.firstcode.ch03.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {

    List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final MsgAdapter adapter = new MsgAdapter(messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        messages.add(new Message(Message.MSG_TYPE_RECV, "HELLO,How are you!!HELLO,How are you!!HELLO,How are you!!HELLO,How are you!!HELLO,How are you!!HELLO,How are you!!"));
        messages.add(new Message(Message.MSG_TYPE_SEND, "HELLO\nxx"));
        messages.add(new Message(Message.MSG_TYPE_RECV, "NI HAO"));
        messages.add(new Message(Message.MSG_TYPE_SEND, "NI HAO"));
        adapter.notifyDataSetChanged();

        final EditText et = (EditText) findViewById(R.id.edit_msg);
        Button btn = (Button) findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et.getText().toString();
                if (!"".equals(content)) {
                    Message msg = new Message(Message.MSG_TYPE_SEND, content);
                    messages.add(msg);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messages.size() - 1);
                    et.setText("");
                }
            }
        });


    }
}
