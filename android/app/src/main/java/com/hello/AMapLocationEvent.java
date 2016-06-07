package com.hello;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by xiong on 16/1/18.
 */
 class AMapLocationEvent extends Event<AMapLocationEvent> {

    public static final String EVENT_NAME = "locationChange";

    private final double longitude;
    private final double latitude;
    private final String address;

    protected AMapLocationEvent(int viewTag, long timestampMs, double lon, double lat, String addressDesc) {
        super(viewTag, timestampMs);

        longitude = lon;
        latitude = lat;
        address = addressDesc;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putDouble("longitude", longitude);
        eventData.putDouble("latitude", latitude);
        eventData.putString("address", address);
        return eventData;
    }
}
