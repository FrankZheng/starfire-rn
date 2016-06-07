package com.hello;

import android.app.Activity;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;

/**
 * Created by xiong on 16/1/18.
 */
 class AMapView extends MapView implements
        LocationSource,
        AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener {

    private static final String TAG = AMapView.class.getSimpleName();

    private final EventDispatcher mEventDispatcher;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Activity mActivity;
    private double latitude;
    private double longitude;
    private LatLonPoint latLonPoint;
    private GeocodeSearch geocoderSearch;

    public AMapView(ReactContext reactContext, Activity activity) {
        super(reactContext);

        mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        mActivity = activity;

        this.onCreate(null);
        aMap = this.getMap();
        setUpMap();
    }
    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.mark_b));// 设置小蓝点的图标
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        // aMap.setMyLocationType()

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();

                Log.d("Latitude:", Double.toString(latitude));
                Log.d("Longitude:", Double.toString(longitude));
                latLonPoint = new LatLonPoint(latitude, longitude);
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 50,
                        GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系

                geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求

                mlocationClient.stopLocation();
                mlocationClient.onDestroy();
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }
    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mActivity);
            geocoderSearch = new GeocodeSearch(mActivity);
            mLocationOption = new AMapLocationClientOption();

            geocoderSearch.setOnGeocodeSearchListener(this);
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            mLocationOption.setMockEnable(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }
    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {

    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        Log.d(TAG, "onRegeocodeSearched, " + rCode);
        String desc = null;
        RegeocodeAddress address = null;
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                address = result.getRegeocodeAddress();
                desc = address.getFormatAddress();
//                if(address!=null){
//                    String poiTitle = getPoiItemTitle(address);
//                    String street = getStreet(address);
//                    String format = getFormat(address);
//                    /**优先顺序*/
//                    /**兴趣点位置*/
//                    if(!TextUtils.isEmpty(poiTitle)){
//                        desc = poiTitle;
//                    }
//                    /**建筑物位置*/
//                    else if(!TextUtils.isEmpty(address.getBuilding())){
//                        desc = address.getBuilding();
//                    }
//                    /**社区位置*/
//                    else if(!TextUtils.isEmpty(address.getNeighborhood())){
//                        desc = address.getNeighborhood();
//                    }
//                    /**街道位置*/
//                    else if(!TextUtils.isEmpty(street)){
//                        desc = street;
//                    }
//                    /**总位置*/
//                    else if(!TextUtils.isEmpty(format)){
//                        desc = result.getRegeocodeAddress().getFormatAddress();
//                    }else {
//                        desc = null;
//                    }
//                }else {
//                    desc = null;
//                }

                if(desc == null){
                    desc = "位置未知！";
                }

                mEventDispatcher.dispatchEvent(
                        new AMapLocationEvent(getId(),
                                SystemClock.uptimeMillis(),
                                longitude,
                                latitude,
                                desc));


                Log.d("地址：", desc);
            }
        }
    }
    /***
     * 街道地址查询
     * @param address
     * @return
     */
    private String getStreet(RegeocodeAddress address) {
        String desc = null;
        if (address != null && address.getStreetNumber()!=null &&
                !TextUtils.isEmpty(address.getStreetNumber().getStreet())) {
            StringBuilder sb = new StringBuilder();
            String number = address.getStreetNumber().getNumber();
            if (!TextUtils.isEmpty(number)) {
                sb.append(address.getStreetNumber().getStreet()).append(number);
                if (!number.endsWith("号")) {
                    sb.append("号");
                }
            } else {
                sb.append(address.getStreetNumber().getStreet());
            }
            desc = sb.toString();
        }
        return desc;
    }

    /***
     * 兴趣点地址查询
     * @param address
     * @return
     */
    private String getPoiItemTitle(RegeocodeAddress address) {
        String poiTitle = null;
        if(address.getPois()!=null && address.getPois().size()>0){
            PoiItem mPoiItem = address.getPois().get(0);
            if(mPoiItem!=null && !TextUtils.isEmpty(mPoiItem.getTitle())){
                poiTitle = mPoiItem.getTitle();
            }
        }
        return poiTitle;
    }
    /**
     * 返回正规地址
     * @param address
     * @return
     */
    private String getFormat(RegeocodeAddress address){
        String desc = null;
        if(address != null
                &&!TextUtils.isEmpty(address.getFormatAddress())){
            desc = address.getFormatAddress();
        }
        return desc;
    }

}
