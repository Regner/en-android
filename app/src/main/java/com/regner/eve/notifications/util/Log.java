package com.regner.eve.notifications.util;

public final class Log {
    
    public static final String TAG = "EveNotifications";
    
    //ProGuard relies on those constants at build time.
    public static final boolean D = false;
    public static final boolean W = false;

    private Log() {
    }

    public static void d(String m, Throwable t) {
        if (!D) {
            return;
        }
        android.util.Log.d(TAG, (null == m) ? "null" : m, t);
    }

    public static void d(Throwable t) {
        if (!D) {
            return;
        }
        android.util.Log.d(TAG, "", t);
    }

    public static void d(String m) {
        if (!D) {
            return;
        }
        android.util.Log.d(TAG, (null == m) ? "null" : m);
    }

    public static void w(String m, Throwable t) {
        if (!W) {
            return;
        }
        android.util.Log.w(TAG, (null == m) ? "null" : m, t);
    }

    public static void w(String m) {
        if (!W) {
            return;
        }
        android.util.Log.w(TAG, (null == m) ? "null" : m);
    }

    public static void w(Throwable t) {
        if (!W) {
            return;
        }
        android.util.Log.w(TAG, t);
    }

    public static void e(String m, Throwable t) {
        android.util.Log.e(TAG, (null == m) ? "null" : m, t);
    }

    public static void e(String m) {
        android.util.Log.e(TAG, (null == m) ? "null" : m);
    }

    public static void i(String m) {
        android.util.Log.i(TAG, (null == m) ? "null" : m);
    }
}
