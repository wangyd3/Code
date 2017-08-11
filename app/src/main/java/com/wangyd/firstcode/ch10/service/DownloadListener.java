package com.wangyd.firstcode.ch10.service;

/**
 * Created by wangyd on 2017/6/22.
 */

public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();

}
