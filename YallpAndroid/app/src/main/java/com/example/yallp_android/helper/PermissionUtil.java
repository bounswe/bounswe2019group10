package com.example.yallp_android.helper;

import android.app.Activity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public final static int READ_PERMISSION = 261;
    public final static int WRITE_PERMISSION = 609;
    public static boolean checkWritePermission(Activity activity) {
        return checkPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE", WRITE_PERMISSION);
    }

    public static boolean checkReadPermission(Activity activity) {
        return checkPermission(activity, "android.permission.READ_EXTERNAL_STORAGE", READ_PERMISSION);
    }

    public static boolean checkPermission(Activity activity, String str, int i) {
        if (ContextCompat.checkSelfPermission(activity, str) == 0) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{str}, i);
            return false;
        }
    }
}
