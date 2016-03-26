package co.tton.mlibrary.util;


import android.util.Log;

import co.tton.mlibrary.base.BaseApplication;
import co.tton.mlibrary.system.SystemSettings;


public class LogUtil {

    private final static String DEFAULT_TAG = BaseApplication.mApplicationContext.getPackageName();

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (SystemSettings.Debug.SHOW_DEVELOP_LOG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (SystemSettings.Debug.SHOW_DEVELOP_LOG) {
            Log.w(tag, msg, null);

        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (SystemSettings.Debug.SHOW_DEVELOP_LOG) {
            if (t == null) {
                Log.w(tag, msg);
            } else {
                Log.w(tag, msg, t);
            }
        }
    }

    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (SystemSettings.Debug.SHOW_DEVELOP_LOG) {
            Log.e(tag, msg, null);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (SystemSettings.Debug.SHOW_DEVELOP_LOG) {
            if (t == null) {
                Log.e(tag, msg);
            } else {
                Log.e(tag, msg, t);
            }
        }
    }


}
