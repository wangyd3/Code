package com.wangyd.firstcode.ch09.webview;

/**
 * Created by wangyd on 2017/6/22.
 */

public interface HttpCallbackListener {
    void onError(Exception e);

    void onFinish(String response);
}
