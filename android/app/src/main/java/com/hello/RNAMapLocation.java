package com.hello;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by lishaoyuan on 5/4/16.
 */
public class RNAMapLocation extends ReactContextBaseJavaModule implements AMapLocationListener {
    private static final String TAG = RNAMapLocation.class.getSimpleName();

    ReactApplicationContext reactContext;
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mLocationClient = null;

    //定位成功或失败的异步回调函数
    private Callback successCallback = null;
    private Callback failCallback = null;

    public RNAMapLocation(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAMapLocation";
    }

    @ReactMethod
    public void getPosition(Callback successCallback, Callback failCallback) {
        Log.d(TAG, "getPosition");
        if(mLocationClient == null) {
            mLocationClient = createOnceLocationClient();
        }
        mLocationClient.startLocation();

        this.successCallback = successCallback;
        this.failCallback = failCallback;
    }

    private AMapLocationClient createOnceLocationClient() {
        AMapLocationClient aMapLocationClient= new AMapLocationClient(this.reactContext);
        //初始化定位参数
        AMapLocationClientOption aMapLocationClientOption= new AMapLocationClientOption();
        //设置定位监听
        aMapLocationClient.setLocationListener(this);
        //设置定位模式Hight_Accuracy为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置为单次定位
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClientOption.setMockEnable(true);
        //设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        return aMapLocationClient;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.d(TAG, "onLocationChanged");
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                WritableMap positionData = getFormatPositionData(aMapLocation);
                this.successCallback.invoke(positionData);
            } else {
                this.failCallback.invoke(aMapLocation.getErrorInfo());
            }
        }
    }

    private WritableMap getFormatPositionData(AMapLocation aMapLocation) {
        WritableMap positionData = Arguments.createMap();
        positionData.putDouble("latitude", aMapLocation.getLatitude());
        positionData.putDouble("longitude", aMapLocation.getLongitude());
        positionData.putDouble("altitude", aMapLocation.getAltitude());
        positionData.putDouble("accuracy", aMapLocation.getAccuracy());
        positionData.putDouble("speed", aMapLocation.getSpeed());
        positionData.putString("address", aMapLocation.getAddress());
        return positionData;
    }
}

