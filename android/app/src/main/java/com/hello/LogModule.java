package com.hello;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by shixiaohou on 16/5/20.
 */
public class LogModule extends ReactContextBaseJavaModule {
    public LogModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Log";
    }

    @ReactMethod
    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    @ReactMethod
    public void e(String tag, String message) {
        Log.e(tag, message);
    }
}
