package com.wangyd.firstcode.Utils;

import android.util.Log;

/**
 * 主要是Debug log的输出
 *
 * @author WangYD
 * @time 2015年8月21日
 */
public class DBG {

    public static final String TAG = "DBG";
    public static final boolean D = true;
    public static final long START = System.currentTimeMillis();

    public static void log(String tag, String msg) {
        if (D) {
            Log.i(TAG, tag + "-> " + msg);
            Log.i(tag, msg);
        }
    }

    public static void log(String tag, String msg, Throwable tr) {
        if (D) {
            Log.e(TAG, tag + "-> " + msg, tr);
            Log.e(tag, msg, tr);
        }
    }

    public static void log(String tag, String msg, Object... args) {
        if (D) {
            String s = String.format(msg, args);
            Log.i(TAG, tag + "-> " + s);
            Log.i(tag, s);
        }
    }


    /**
     * 当前文件名
     *
     * @return
     */
    public static String _FILE_() {
        if (!D)
            return "release";
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getFileName();
    }

    /**
     * 当前方法名
     *
     * @return
     */
    public static String _FUNC_() {
        if (!D)
            return "release";
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    /**
     * 当前行号
     *
     * @return
     */
    public static int _LINE_() {
        if (!D)
            return 0;
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }


}
