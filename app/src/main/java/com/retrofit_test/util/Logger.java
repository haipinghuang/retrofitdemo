package com.retrofit_test.util;

import android.content.res.TypedArray;
import android.util.Log;
import android.util.TypedValue;

import com.retrofit_test.BuildConfig;

import java.lang.reflect.Array;

/**
 * Created by 黄海 on 12/25/2016.
 */
public class Logger {
    public static boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "[**####log####**],";
    private static final String SEPARATOR = "***";
    private static int ZERO = -1;

    Logger() {
    }

    public static int v(String tag, String msg) {
        if (DEBUG) return Log.v(TAG, tag + SEPARATOR + msg);
        else return ZERO;
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (DEBUG) return Log.v(TAG, tag + SEPARATOR + msg, tr);
        else return ZERO;
    }

    public static int d(String msg) {
        if (DEBUG) return Log.d(TAG, msg);
        else return ZERO;
    }

    public static int d(String tag, String msg) {
        if (DEBUG) return Log.d(TAG, tag + SEPARATOR + msg);
        else return ZERO;
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (DEBUG) return Log.d(TAG, tag + SEPARATOR + msg, tr);
        else return ZERO;
    }

    public static int i(String msg) {
        if (DEBUG) return Log.i(TAG, msg);
        else return ZERO;
    }

    public static int i(String tag, String msg) {
        if (DEBUG) return Log.i(TAG, tag + SEPARATOR + msg);
        else return ZERO;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (DEBUG) return Log.i(TAG, tag + SEPARATOR + msg, tr);
        else return ZERO;
    }

    public static int w(String tag, String msg) {
        if (DEBUG) return Log.w(TAG, tag + SEPARATOR + msg);
        else return ZERO;
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (DEBUG) return Log.w(TAG, tag + SEPARATOR + msg, tr);
        else return ZERO;
    }

    public static int e(String msg) {
        if (DEBUG) return Log.e(TAG, msg);
        else return ZERO;
    }

    public static int e(String tag, String msg) {
        if (DEBUG) return Log.e(TAG, tag + SEPARATOR + msg);
        else return ZERO;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (DEBUG) return Log.e(TAG, tag + SEPARATOR + msg, tr);
        else return ZERO;
    }
}
