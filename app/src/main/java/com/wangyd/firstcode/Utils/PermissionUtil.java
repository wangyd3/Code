package com.wangyd.firstcode.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.wangyd.firstcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyd on 2017/8/1.
 */

public class PermissionUtil {
    private final static String TAG = "PermissionUtil";
    private static PermissionUtil permissionUtil = null;
    private static List<String> mListPermissions;
    private static final String PERMISSIONS_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERMISSIONS_PHONE = Manifest.permission.READ_PHONE_STATE;
    private static final String PERMISSIONS_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    private static final String PERMISSIONS_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERMISSIONS_AUDIO = Manifest.permission.RECORD_AUDIO;

    /**
     * 添加权限
     * author LH
     * data 2016/7/27 11:27
     */
    private void addAllPermissions(List<String> mListPermissions) {
        mListPermissions.add(PERMISSIONS_STORAGE);
        mListPermissions.add(PERMISSIONS_PHONE);
        mListPermissions.add(PERMISSIONS_ACCOUNTS);
        mListPermissions.add(PERMISSIONS_LOCATION);
        mListPermissions.add(PERMISSIONS_AUDIO);
    }

    /**
     * 单例模式初始化
     * author LH
     * data 2016/7/27 11:27
     */
    public static PermissionUtil getInstance() {
        if (permissionUtil == null) {
            permissionUtil = new PermissionUtil();
        }
        return permissionUtil;
    }

    /**
     * 判断当前为M以上版本
     * author LH
     * data 2016/7/27 11:29
     */
    public boolean isOverMarshmallow() {
        //return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        return true;
    }

    /**
     * 获得没有授权的权限
     * author LH
     * data 2016/7/27 11:46
     */
    public List<String> findDeniedPermissions(Activity activity, List<String> permissions) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permissions) {
            if (ContextCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 获取所有权限
     * author LH
     * data 2016/7/27 13:37
     */

    public void requestPermissions(Activity activity, int requestCode, PermissionCallBack permissionCallBack) {
        if (mListPermissions == null) {
            mListPermissions = new ArrayList<>();
            addAllPermissions(mListPermissions);
        }

        DBG.log(TAG, "mListPermissions=" + mListPermissions.size());
        if (!isOverMarshmallow()) {
            DBG.log(TAG, "mListPermissions=return");
            return;
        }
        mListPermissions = findDeniedPermissions(activity, mListPermissions);
        DBG.log(TAG, "mListPermissions=" + mListPermissions.size());

        if (mListPermissions != null && mListPermissions.size() > 0) {
            ActivityCompat.requestPermissions(activity, mListPermissions.toArray(new String[mListPermissions.size()]), requestCode);
        } else {
            permissionCallBack.onPermissionSuccess();
        }
    }

    public void requestResult(Activity activity, String[] permissions, int[] grantResults, PermissionCallBack permissionCallBack) {
        mListPermissions = new ArrayList<>();
        ArrayList<String> noPermissions = new ArrayList<>();
        ArrayList<String> rejectPermissons = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (isOverMarshmallow()) {
                    boolean isShould = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                    mListPermissions.add(permissions[i]);
                    if (isShould) {
                        noPermissions.add(permissions[i]);
                    } else {
                        rejectPermissons.add(permissions[i]);
                    }
                }
            }
        }

        if (noPermissions.size() > 0) {
            permissionCallBack.onPermissionFail();
        } else if (rejectPermissons.size() > 0) {
            ArrayList<String> permissonsList = new ArrayList<>();
            for (int i = 0; i < rejectPermissons.size(); i++) {
                String strPermissons = rejectPermissons.get(i);
                if (PERMISSIONS_STORAGE.equals(strPermissons)) {
                    permissonsList.add(activity.getString(R.string.permission_storage));
                } else if (PERMISSIONS_ACCOUNTS.equals(strPermissons)) {
                    permissonsList.add(activity.getString(R.string.permission_accounts));
                } else if (PERMISSIONS_PHONE.equals(strPermissons)) {
                    permissonsList.add(activity.getString(R.string.permission_phone));
                } else if (PERMISSIONS_LOCATION.equals(strPermissons)) {
                    permissonsList.add(activity.getString(R.string.permission_location));
                } else if (PERMISSIONS_AUDIO.equals(strPermissons)) {
                    permissonsList.add(activity.getString(R.string.permission_audio));
                }
            }
            String strPermissons = permissonsList.toString();
            strPermissons = strPermissons.replace("[", "");
            strPermissons = strPermissons.replace("]", "");
            strPermissons = strPermissons.replace(",", "、");
            String strAppName = activity.getString(R.string.app_name);
            String strMessage = activity.getString(R.string.permission_message);

            strMessage = strMessage + strAppName + strPermissons;

            //strMessage = String.format(strMessage, strAppName, strPermissons, "\"");
            permissionCallBack.onPermissionReject(strMessage);

        } else {
            permissionCallBack.onPermissionSuccess();
        }
    }

    public interface PermissionCallBack {
        void onPermissionSuccess();

        void onPermissionReject(String strMessage);

        void onPermissionFail();
    }
}