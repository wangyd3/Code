package com.wangyd.firstcode.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyd on 2017/6/9.
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity aty) {
        activities.add(aty);
    }

    public static void removeActivity(Activity aty) {
        activities.remove(aty);
    }

    public static void finishAll() {
        for (Activity aty : activities) {
            if (!aty.isFinishing()) {
                aty.finish();
            }
        }
    }

}
