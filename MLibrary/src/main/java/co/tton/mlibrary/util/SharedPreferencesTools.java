package co.tton.mlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2015/10/12.
 *
 */
public class SharedPreferencesTools {

    private Context context;
    private SharedPreferences sp = null;
    private SharedPreferences.Editor edit = null;
    /**
     * Create DefaultSharedPreferences
     */
    public SharedPreferencesTools(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
    }

    /**
     * Create SharedPreferences by filename
     */
    public SharedPreferencesTools(Context context, String filename) {
        this(context, context.getSharedPreferences(filename, Context.MODE_PRIVATE));
    }

    /**
     * Create SharedPreferences by SharedPreferences
     */
    public SharedPreferencesTools(Context context, SharedPreferences sp) {
        this.context = context;
        this.sp = sp;

    }

    // Set

    // Boolean
    public void setValue(String key, boolean value) {
        edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public void setValue(int resKey, boolean value) {
        setValue(this.context.getString(resKey), value);
    }

    // Float
    public void setValue(String key, float value) {
        edit = sp.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    public void setValue(int resKey, float value) {
        setValue(this.context.getString(resKey), value);
    }

    // Integer
    public void setValue(String key, int value) {
        edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public void setValue(int resKey, int value) {
        setValue(this.context.getString(resKey), value);
    }

    // Long
    public void setValue(String key, long value) {
        edit = sp.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public void setValue(int resKey, long value) {
        setValue(this.context.getString(resKey), value);
    }

    // String
    public void setValue(String key, String value) {
        edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void setValue(int resKey, String value) {
        setValue(this.context.getString(resKey), value);
    }

    // Get

    // Boolean
    public boolean getValue(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public boolean getValue(int resKey, boolean defaultValue) {
        return getValue(this.context.getString(resKey), defaultValue);
    }

    // Float
    public float getValue(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public float getValue(int resKey, float defaultValue) {
        return getValue(this.context.getString(resKey), defaultValue);
    }

    // Integer
    public int getValue(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public int getValue(int resKey, int defaultValue) {
        return getValue(this.context.getString(resKey), defaultValue);
    }

    // Long
    public long getValue(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public long getValue(int resKey, long defaultValue) {
        return getValue(this.context.getString(resKey), defaultValue);
    }

    // String
    public String getValue(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public String getValue(int resKey, String defaultValue) {
        return getValue(this.context.getString(resKey), defaultValue);
    }

    // Delete
    public void remove(String key) {
        edit = sp.edit();
        edit.remove(key);
        edit.apply();
    }

    public void clear() {
        edit = sp.edit();
        edit.clear();
        edit.apply();
    }

}
