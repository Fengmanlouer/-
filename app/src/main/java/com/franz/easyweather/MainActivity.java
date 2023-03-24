package com.franz.easyweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.franz.easyweather.adapter.Weather24HAdapter;
import com.franz.easyweather.adapter.Weather7DAdapter;
import com.franz.easyweather.bean.WFuture7Bean;
import com.franz.easyweather.bean.WHourly24Bean;
import com.franz.easyweather.bean.WRealTimeBean;

import com.franz.easyweather.callback.WeatherCallback_24H;
import com.franz.easyweather.callback.WeatherCallback_7D;
import com.franz.easyweather.callback.WeatherCallback_now;
import com.franz.easyweather.databinding.ActivityMainBinding;
import com.franz.easyweather.utils.JsonUtils;
import com.franz.easyweather.utils.LocationUtils;
import com.franz.easyweather.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding binding;
    private static final String TAG = "MainActivityLog";
    private static final int WEATHER_NOW = 0;
    private static final int WEATHER_24H = 1;
    private static final int WEATHER_7D = 2;
    private static final int REQUEST_CODE_LOCATION = 200;
    //定位
    private static final String[] Group_Location = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private Weather24HAdapter adapter24H;
    private Weather7DAdapter adapter7D;
    //根据提示加上了关键词final
    final private List<WHourly24Bean> hourlyBeanList = new ArrayList<>();
    //根据提示加上了关键词final
    final private List<WFuture7Bean> dailyBeanList = new ArrayList<>();
    private String location;


    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WEATHER_NOW:
                    WRealTimeBean bean = (WRealTimeBean) msg.obj;
                    updateNowWeather(bean);
                    break;
                case WEATHER_24H:
                    hourlyBeanList.addAll ((List<WHourly24Bean>) msg.obj);
                    adapter24H.notifyDataSetChanged();
                    break;
                case WEATHER_7D:
                    dailyBeanList.addAll ((List<WFuture7Bean>) msg.obj);
                    adapter7D.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initPermission();
        //initSDK();
        initRecycler();

    }

    private void initPermission() {
        if (checkSelfPermission(Group_Location[0]) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Group_Location[1]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(Group_Location, REQUEST_CODE_LOCATION);
        } else {
            getWeatherInfo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            getWeatherInfo();
            Log.d(TAG, "apply for success");
        }
    }

    /*private void initSDK() {
        //配置id和key
        HeConfig.init(WeatherParam.weatherID, WeatherParam.weatherKey);
        //切换至开发版服务
        HeConfig.switchToDevService();
    }
     */

    private void initRecycler() {
        binding.Recycler24H.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter24H = new Weather24HAdapter(hourlyBeanList);
        binding.Recycler24H.setAdapter(adapter24H);

        binding.Recycler7D.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter7D = new Weather7DAdapter(dailyBeanList);
        binding.Recycler7D.setAdapter(adapter7D);
    }

    private void updateNowWeather(WRealTimeBean bean) {
        binding.normalCity.setText(location);//城市
        binding.normalTemp.setText(bean.getTemperature() + "°");//温度
        binding.normalStatus.setText(bean.getText());//天气状态
        binding.windSpeed.setText(bean.getWindSpeed() + "km/h");//风速
        binding.windDirection.setText(bean.getWindDirection()+bean.getWindDirectionDegree()+ "°");//风向
        binding.cloud.setText(bean.getClouds() + "%");//云量
        binding.bodyTmp.setText(bean.getFeelsLike() + "°");//体感温度
        binding.hum.setText(bean.getHumidity() + "%");//湿度
        binding.visibility.setText(bean.getVisibility() + "km");//可见度
        binding.pressure.setText(bean.getPressure() + "mb");//气压

        changeBG(bean.getText());
    }

//根据老师的提示，做了修改：
// 根据不同地方天气的描述，动态更换不同的背景图片。增加了中雨，小雨，扬沙，霾等描述。
    private void changeBG(String status){
        switch (status){
            case "晴":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_sunny));break;
            case "多云":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_cloudy));break;
            case "阴":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_dark));break;
            //大雨、中雨背景图片设置为一样
            case "大雨":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_big_rain));break;
            case "中雨":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_big_rain));break;
            //雨，小雨设置为一样
            case "雨":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_small_rain));break;
            case "小雨":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_small_rain));break;
            case "雪":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_snow));break;
            case "雷":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_thunder));break;
            //添加了扬沙、霾等描述
            case "扬沙":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_fog));break;
            case "霾":binding.mainLinearLayout.setBackground(getDrawable(R.drawable.icon_bg_wumai));break;
        }
    }

    //根据位置取得天气信息：
    private void getWeatherInfo(){
        location = LocationUtils.getInstance().getLocationInfo();
        if (TextUtils.isEmpty(location)) {
            binding.NormalStatus.setVisibility(View.GONE);
            binding.nestedScrollView.setVisibility(View.GONE);
            return;
        }
        binding.NormalStatus.setVisibility(View.VISIBLE);
        binding.nestedScrollView.setVisibility(View.VISIBLE);

        getNowWeather(location);
        getFuture7Weather(location);
        getHourly24Weather(location);
    }

    /**
     * 获取当前天气情况
     */
    private void getNowWeather(String location) {
        JsonUtils.INSTANCE.getNowWeather(location, new WeatherCallback_now() {
            @Override
            public void onFailed(int ErrorCode) {

            }

            @Override
            public void onSuccess(WRealTimeBean bean) {
                if (bean != null){
                    Message message = new Message();
                    message.what = WEATHER_NOW;
                    message.obj = bean;
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 获取未来七天天气情况（包括今天）
     */
    private void getFuture7Weather(String location) {
        JsonUtils.INSTANCE.getFuture7Weather(location, new WeatherCallback_7D() {
            @Override
            public void onFailed(int ErrorCode) {

            }

            @Override
            public void onSuccess(List<WFuture7Bean> beanList) {
                if (beanList != null && beanList.size() > 0){
                    Message message = new Message();
                    message.what = WEATHER_7D;
                    message.obj = beanList;
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 获取未来24小时天气情况（包括当前）
     */
    private void getHourly24Weather(String location) {
        JsonUtils.INSTANCE.getHourly24Weather(location, new WeatherCallback_24H() {
            @Override
            public void onFailed(int ErrorCode) {

            }

            @Override
            public void onSuccess(List<WHourly24Bean> beanList) {
                if (beanList != null && beanList.size() > 0){
                    Message message = new Message();
                    message.what = WEATHER_24H;
                    message.obj = beanList;
                    handler.sendMessage(message);
                }
            }
        });
    }


}