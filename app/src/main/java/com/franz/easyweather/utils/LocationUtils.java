package com.franz.easyweather.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.franz.easyweather.base.BaseApplication;


import java.util.List;
import java.util.Locale;

public class LocationUtils {
    private LocationManager locationManager;
    private static LocationUtils spInstant = null;
    private static String TAG = "MainActivityLog";
    private static final String[] Group_Location = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private LocationUtils() {

    }

    public static LocationUtils getInstance() {
        if (spInstant == null) {
            Sync();
        }
        return spInstant;
    }

    private static synchronized void Sync() {
        if (spInstant == null) {
            spInstant = new LocationUtils();
        }
    }

    public String getLocationInfo() {
        if (ActivityCompat.checkSelfPermission(BaseApplication.context,Group_Location[0]) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BaseApplication.context, Group_Location[1]) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Don't apply for these permission");
            return null;
        }

        String strLocation = null;
        try {
            //获取系统的服务，
            locationManager = (LocationManager) BaseApplication.context.getSystemService(Context.LOCATION_SERVICE);
            //创建一个criteria对象
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            //设置不需要获取海拔方向数据
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            //设置允许产生资费
            criteria.setCostAllowed(true);
            //要求低耗电
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location == null)return null;

            //strLocation =  location.getLongitude()+","+location.getLatitude();
            strLocation = convertAddress(BaseApplication.context, location.getLatitude(), location.getLongitude());
            Log.d(TAG, "location is = " + strLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLocation;
    }

    private String convertAddress(Context context, double latitude, double longitude) {
        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> mAddresses = mGeocoder.getFromLocation(latitude, longitude, 1);
            if (mAddresses != null && mAddresses.size() > 0) {
                Address address = mAddresses.get(0);
                Log.d(TAG, "国家 is " + address.getCountryName());
                Log.d(TAG, "省 is " + address.getAdminArea());
                Log.d(TAG, "市 is " + address.getLocality());
                Log.d(TAG, "区/县 is " + address.getSubLocality());
                //Log.d(TAG, "具体 is " + address.getFeatureName());
                //return  address.getLocality();
                //提高精确度
                return address.getSubLocality();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
