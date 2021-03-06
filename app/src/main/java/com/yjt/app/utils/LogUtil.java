package com.yjt.app.utils;

import android.text.TextUtils;

import com.yjt.app.BuildConfig;

/**
 * Log工具
 *
 * @author yjt
 */
public class LogUtil {

    private LogUtil() {
        // cannot be instantiated
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(tag, msg, t);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(tag, msg, t);
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag, msg, t);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(tag, msg, t);
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(tag, msg, t);
    }

    public static void print(String str) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(str)) {
            System.out.println(str);
        }
    }

    public static void print(Object... objects) {
        if (BuildConfig.DEBUG && objects != null && objects.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (Object object : objects) {
                if (objects != null) {
                    System.out.println(builder.append(object.toString()));
                }
            }
        }
    }
}
