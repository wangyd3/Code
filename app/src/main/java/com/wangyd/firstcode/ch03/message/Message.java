package com.wangyd.firstcode.ch03.message;

/**
 * Created by wangyd on 2017/6/13.
 */

public class Message {
    public static final int MSG_TYPE_RECV = 0;
    public static final int MSG_TYPE_SEND = 1;

    private int type;
    private String msg;

    public Message(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
