package com.franz.easyweather.callback;

import com.franz.easyweather.bean.WHourly24Bean;

import java.util.List;

public interface WeatherCallback_24H {
    void onFailed(int ErrorCode);
    void onSuccess(List<WHourly24Bean> beanList);
}
