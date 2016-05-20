package com.hello;

import android.util.Log;

import com.facebook.react.*;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

/**
 * Created by shixiaohou on 16/5/20.
 */
public class AppInfoModule extends ReactContextBaseJavaModule  {
    private static final String TAG = AppInfoModule.class.getSimpleName();

    public AppInfoModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "AppInfo";
    }

    @ReactMethod
    public void getAppInfo(Callback errorCallback, Callback successCallback) {
        Log.d(TAG, "getAppInfo");
        WritableMap map = new WritableNativeMap();
        map.putString("appVersion", BuildConfig.VERSION_NAME);
        map.putInt("appVersionCode", BuildConfig.VERSION_CODE);
        map.putInt("appType", 7);
        map.putInt("osType", 1);
        successCallback.invoke(map);
    }




}
