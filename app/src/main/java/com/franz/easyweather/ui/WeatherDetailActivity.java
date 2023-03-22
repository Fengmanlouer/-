package com.franz.easyweather.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franz.easyweather.R;
import com.franz.easyweather.adapter.BaseFragmentAdapter;
import com.franz.easyweather.adapter.Weather24HAdapter;
import com.franz.easyweather.adapter.Weather7DAdapter;
import com.franz.easyweather.bean.LocationBusBean;
import com.franz.easyweather.bean.PageBean;
import com.franz.easyweather.bean.WFuture7Bean;
import com.franz.easyweather.bean.WHourly24Bean;
import com.franz.easyweather.bean.WRealTimeBean;
import com.franz.easyweather.callback.WeatherCallback_24H;
import com.franz.easyweather.callback.WeatherCallback_7D;
import com.franz.easyweather.callback.WeatherCallback_now;
import com.franz.easyweather.databinding.ActivityWeatherDetailBinding;
import com.franz.easyweather.sql.Dao;
import com.franz.easyweather.utils.JsonUtils;
import com.franz.easyweather.utils.LocationUtils;
import com.franz.easyweather.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class WeatherDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ActivityWeatherDetailBinding binding;

    private static final String TAG = "WeatherDetailActivityLog";

    private static final int WEATHER_Start = 3;
    private static final int REQUEST_CODE_LOCATION = 200;
    //定位
    private static final String[] Group_Location = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    

    private static List<View> viewList = new ArrayList<>();
    private static List<String> titleList = new ArrayList<>();

    private List<String> locationList;



    private int oldPos = 0;

    private Dao dao = null;

    private static String location;

    private static BaseFragmentAdapter adapter;
    private int count = 0;


    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WEATHER_Start:
                    String location = (String) msg.obj;
                    addView(location);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        binding = ActivityWeatherDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBaseFragment();
        initPermission();
        updateIndicator();

        binding.weatherViewPager.addOnPageChangeListener(this);

        /**
         * 默认第0页面与指示器绑定*/
        binding.indicatorLayout.getChildAt(oldPos).setEnabled(true);

        binding.moreLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherDetailActivity.this,MoreLocationActivity.class);
                if (!TextUtils.isEmpty(location)){
                    intent.putExtra("currentLocation",location);
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 权限申明*/
    private void initPermission() {
        if (checkSelfPermission(Group_Location[0]) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Group_Location[1]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(Group_Location, REQUEST_CODE_LOCATION);
        } else {
            initViewPager();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            initViewPager();
        }
    }

    /**
     * 初始化viewPager，为避免空指针，先初始化对象，后面直接notify*/
    private void initBaseFragment(){
        adapter = new BaseFragmentAdapter(viewList,titleList);
        binding.weatherViewPager.setAdapter(adapter);
    }


    /**
     * 根据用户添加的城市，然后添加对应的viewPager页面*/
    private void initViewPager(){
        dao = new Dao(this);
        locationList = dao.QueryAll();
        location = LocationUtils.getInstance().getLocationInfo();

        if (locationList == null || locationList.size() == 0){
            locationList = new ArrayList<>();
            locationList.add(location);
        }else {
            locationList.add(0,location);
        }

        for (int i = 0; i < locationList.size(); i++) {
            Message message = new Message();
            message.what = WEATHER_Start;
            message.obj = locationList.get(i);
            handler.sendMessage(message);
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * viewPager页面添加，包括实况天气数据、预期24小时、预报7天*/
    private void addView(String location){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_main,null,false);

        LinearLayout weatherLayout;
        TextView weatherLocation,weatherTemp,weatherStatus,weatherWindDirection,weatherWindSpeed,weatherCloud,weatherFellLikes,weatherHum,weatherVisibility,weatherPressure;
        RecyclerView weather7D,weather24H;

        weatherLayout = view.findViewById(R.id.mainLinearLayout);
        weatherLocation = view.findViewById(R.id.normal_city);
        weatherTemp = view.findViewById(R.id.normal_temp);
        weatherStatus = view.findViewById(R.id.normal_status);
        weatherWindDirection = view.findViewById(R.id.windDirection);
        weatherWindSpeed = view.findViewById(R.id.windSpeed);
        weatherCloud = view.findViewById(R.id.cloud);
        weatherFellLikes = view.findViewById(R.id.bodyTmp);
        weatherHum = view.findViewById(R.id.hum);
        weatherVisibility = view.findViewById(R.id.visibility);
        weatherPressure = view.findViewById(R.id.pressure);
        weather7D = view.findViewById(R.id.Recycler_7D);
        weather24H = view.findViewById(R.id.Recycler_24H);


        List<WHourly24Bean> hourlyBeanList = new ArrayList<>();
        List<WFuture7Bean> dailyBeanList = new ArrayList<>();

        weather24H.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Weather24HAdapter  adapter24H = new Weather24HAdapter(hourlyBeanList);
        weather24H.setAdapter(adapter24H);

        weather7D.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Weather7DAdapter adapter7D = new Weather7DAdapter(dailyBeanList);
        weather7D.setAdapter(adapter7D);

        viewList.add(view);
        titleList.add(location);

        adapter.notifyDataSetChanged();

        weatherLocation.setText(location);


        JsonUtils.INSTANCE.getNowWeather(location, new WeatherCallback_now() {
            @Override
            public void onFailed(int ErrorCode) {

            }

            @Override
            public void onSuccess(WRealTimeBean bean) {
                if (bean != null){
                    runOnUiThread(()->{
                        weatherTemp.setText(bean.getTemperature() + "°");//温度
                        weatherStatus.setText(bean.getText());//天气状态
                        weatherWindSpeed.setText(bean.getWindSpeed() + "km/h");//风速
                        weatherWindDirection.setText(bean.getWindDirection()+bean.getWindDirectionDegree()+ "°");//风向
                        weatherCloud.setText(bean.getClouds() + "%");//云量
                        weatherFellLikes.setText(bean.getFeelsLike() + "°");//体感温度
                        weatherHum.setText(bean.getHumidity() + "%");//湿度
                        weatherVisibility.setText(bean.getVisibility() + "km");//可见度
                        weatherPressure.setText(bean.getPressure() + "mb");//气压

                        switch (bean.getText()){
                            case "晴":weatherLayout.setBackground(getDrawable(R.drawable.icon_bg_sunny));break;
                            case "多云":weatherLayout.setBackground(getDrawable(R.drawable.icon_bg_cloudy));break;
                            case "阴":weatherLayout.setBackground(getDrawable(R.drawable.icon_bg_cloudy));break;
                            case "雨":weatherLayout.setBackground(getDrawable(R.drawable.icon_bg_big_rain));break;
                            case "雪":weatherLayout.setBackground(getDrawable(R.drawable.icon_bg_snow));break;
                            case "雷":weatherLayout.setBackground(getDrawable(R.drawable.icon_bg_thunder));break;
                        }
                    });
                }else {
                    Log.d(TAG,"bean is empty");
                }
            }
        });

        JsonUtils.INSTANCE.getFuture7Weather(location, new WeatherCallback_7D() {
            @Override
            public void onFailed(int ErrorCode) {

            }

            @Override
            public void onSuccess(List<WFuture7Bean> beanList) {
                if (beanList != null && beanList.size() > 0){
                    if (dailyBeanList != null && dailyBeanList.size() > 0){
                        dailyBeanList.clear();
                    }
                    dailyBeanList.addAll(beanList);
                    runOnUiThread(()->{
                        adapter7D.notifyDataSetChanged();
                    });
                }
            }
        });

        JsonUtils.INSTANCE.getHourly24Weather(location, new WeatherCallback_24H() {
            @Override
            public void onFailed(int ErrorCode) {

            }

            @Override
            public void onSuccess(List<WHourly24Bean> beanList) {
                if (beanList != null && beanList.size() > 0){
                    if (hourlyBeanList != null && hourlyBeanList.size() > 0){
                        hourlyBeanList.clear();
                    }
                    hourlyBeanList.addAll(beanList);
                    runOnUiThread(()->{
                        adapter24H.notifyDataSetChanged();
                    });
                }
            }
        });
    }

    /**
     * 每添加或删除一个viewPager子项，然后更新指示器个数*/
    private void updateIndicator(){
        binding.indicatorLayout.removeAllViews();
        List<String> list = dao.QueryAll();

        /**
         * 因为有一个本地位置，所以需要+1*/
        int size = 1;
        if (list != null){
            size = list.size()+1;
        }
        for (int i = 0; i < size; i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.selector_indicator);
            view.setEnabled(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,30);
            params.rightMargin = 15;
            params.leftMargin = 15;
            binding.indicatorLayout.addView(view,params);
        }
    }

    /**
     * 删除viewPager子项*/
    private void removeView(String location){
        int position = titleList.indexOf(location);
        if (position != -1){
            viewList.remove(position);
            titleList.remove(position);
            adapter.notifyDataSetChanged();
        }

        updateIndicator();
    }

    /**
     * 从城市添加页面跳转过来，根据所携带的城市名，切换相对应的指示器位置*/
    private void updateCurIndicator(int newPos){
        if (binding.indicatorLayout == null)return;
        binding.indicatorLayout.getChildAt(oldPos).setEnabled(false);
        binding.indicatorLayout.getChildAt(newPos).setEnabled(true);
        oldPos = newPos;
    }

    /**
     * 在城市页面进行数据添加或删除，使用EventBus进行监测*/
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(LocationBusBean bean){
        if (bean != null){
            if (bean.getDeletePos() == -1){
                addView(bean.getLocation());
                updateIndicator();
            }else {
                removeView(bean.getLocation());
            }
        }
    }

    /**
     * 在城市页面点击城市子项进行跳转，动态更新指示器和显示相对应的view*/
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(PageBean bean){
        if (bean != null){
            List<String> list = dao.QueryAll();
            if (bean.getLocation().equals(location)){
                binding.weatherViewPager.setCurrentItem(0);
                updateCurIndicator(0);
                oldPos = 0;
            }else if (list != null){
                int position = list.indexOf(bean.getLocation());
                if (position != -1){
                    binding.weatherViewPager.setCurrentItem(position+1);
                    updateCurIndicator(position+1);
                    oldPos = position+1;
                }
            }else {
                binding.weatherViewPager.setCurrentItem(0);
                updateCurIndicator(0);
                oldPos = 0;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        binding.indicatorLayout.getChildAt(oldPos).setEnabled(false);
        binding.indicatorLayout.getChildAt(position).setEnabled(true);
        oldPos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}