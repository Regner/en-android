package com.regner.eve.notifications.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class PreferenceSupport {

    public static void register(final Context context, final OnSharedPreferenceChangeListener l) {
        getPreferences(context).registerOnSharedPreferenceChangeListener(l);
    }

    public static void unregister(final Context context, final OnSharedPreferenceChangeListener l) {
        getPreferences(context).unregisterOnSharedPreferenceChangeListener(l);
    }

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static void setBoolean(final Context context, final String key, final boolean value) {
        getPreferences(context).edit().putBoolean(key, value).commit();
    }

    public static void setInt(final Context context, final String key, final int value) {
        getPreferences(context).edit().putInt(key, value).commit();
    }

    public static void setLong(final Context context, final String key, final long value) {
        getPreferences(context).edit().putLong(key, value).commit();
    }

    public static void setString(final Context context, final String key, final String value) {
        getPreferences(context).edit().putString(key, value).commit();
    }

    public static void remove(final Context context, final String key) {
        getPreferences(context).edit().remove(key).commit();
    }

    public static String getString(final Context context, final String key, final String defaultValue) {
        return getString(getPreferences(context), key, defaultValue);
    }

    public static String getString(final SharedPreferences prefs, final String key, final String defaultValue) {
        try {
            return prefs.getString(key, defaultValue);
        }
        catch (ClassCastException e) {
            if (Log.D)
                Log.d("Preferences:getString(" + key + "):" + e.getMessage());
            return defaultValue;
        }
    }

    public static boolean getBoolean(final Context context, final String key, final boolean defaultValue) {
        return getBoolean(getPreferences(context), key, defaultValue);
    }

    public static boolean getBoolean(final SharedPreferences prefs, final String key, final boolean defaultValue) {
        try {
            return prefs.getBoolean(key, defaultValue);
        }
        catch (ClassCastException e) {
            if (Log.D)
                Log.d("getBoolean(" + key + "):" + e.getMessage());
            return defaultValue;
        }
    }

    public static int getInt(final Context context, final String key, final int defaultValue) {
        return getInt(getPreferences(context), key, defaultValue);
    }

    public static int getInt(final SharedPreferences prefs, final String key, final int defaultValue) {
        try {
            return prefs.getInt(key, defaultValue);
        }
        catch (ClassCastException e) {
            if (Log.D)
                Log.d("Preferences:getInt(" + key + "):" + e.getMessage());
            //DONT return
        }
        try {
            return Integer.parseInt(prefs.getString(key, "" + defaultValue));
        }
        catch (NumberFormatException e) {
            if (Log.D)
                Log.d("Preferences:getInt(" + key + "):" + e.getMessage());
            throw e;
        }
    }

    public static long getLong(final Context context, final String key, final long defaultValue) {
        return getPreferences(context).getLong(key, defaultValue);
    }

    public static void setLongArray(final Context context, final String key, final long[] array) {
        if (ArrayUtils.isEmpty(array)) {
            getPreferences(context).edit().remove(key).commit();
            return;
        }

        final StringBuilder b = new StringBuilder();
        for (long l: array) {
            b.append(Long.toString(l));
            b.append(",");
        }
        getPreferences(context).edit().putString(key, StringUtils.removeEnd(b.toString(), ",")).commit();
    }

    public static long[] getLongArray(final Context context, final String key) {
        final String value = getPreferences(context).getString(key, null);
        if (StringUtils.isBlank(value)) {
            return new long[0];
        }

        final String[] split = StringUtils.split(value, ",");
        if (ArrayUtils.isEmpty(split)) {
            return new long[0];
        }
        try {
            final long[] returned = new long[split.length];
            for (int i = 0; i < split.length; i++) {
                returned[i] = Long.parseLong(split[i]);
            }
            return returned;
        }
        catch (NumberFormatException e) {
            Log.w(e.getLocalizedMessage(), e);
            Log.e(e.getLocalizedMessage());
            return new long[0];
        }
    }
}
