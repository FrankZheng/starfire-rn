package com.hello;

import android.app.Activity;
import android.util.Log;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

/**
 * Created by xiong on 16/1/16.
 */

public class AMapViewManager extends SimpleViewManager<AMapView> {

    @VisibleForTesting
    public static final String RCT_CLASS = "RCTAMap";

    public static final String TAG = "RCTAMap";

    private Activity mActivity;
    private AMapView aMapView;
    private MarkerOptions markerOption;

    public AMapViewManager(Activity activity) {
        mActivity = activity;
    }

    @Override
    public String getName() {
        return RCT_CLASS;
    }

    @Override
    protected AMapView createViewInstance(ThemedReactContext reactContext) {

        aMapView = new AMapView(reactContext, mActivity);
        return  aMapView;
    }


    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                AMapLocationEvent.EVENT_NAME, MapBuilder.of("registrationName", "onRegionChange")
        );
    }


    @ReactProp(name="mode", defaultInt = 1)
    public void setMode(AMapView mapView, int type) {

        Log.d(TAG, "mode:" + type);
    }

    @ReactProp(name="showUserLocation", defaultBoolean = false)
    public void showUserLocation(AMapView mapView, Boolean value) {
        if(value) {
            mapView.getMap().setMyLocationEnabled(true);
        }
    }

    @ReactProp(name="annotations", defaultInt = 1)
    public void setMarker(AMapView mapView, ReadableArray array) {

        Log.d(TAG, "annotations:" + array);
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                ReadableMap annotation = array.getMap(i);
                MarkerOptions marker = new MarkerOptions();

                double latitude = annotation.getDouble("latitude");
                double longitude = annotation.getDouble("longitude");
                LatLng markerCenter = new LatLng(latitude, longitude);
                marker.position(markerCenter);

                if (annotation.hasKey("title")) {
                    String title = annotation.getString("title");
                    marker.title(title);
                }

                if (annotation.hasKey("subtitle")) {
                    String subtitle = annotation.getString("subtitle");
                    marker.snippet(subtitle);
                }

                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_b));
                mapView.getMap().addMarker(marker);
                mapView.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(markerCenter, 15));
            }
        }
    }




}
