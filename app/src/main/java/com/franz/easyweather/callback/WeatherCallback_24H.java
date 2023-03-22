package com.franz.easyweather.callback;

import com.franz.easyweather.bean.WFuture7Bean;
import com.franz.easyweather.bean.WHourly24Bean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;

import java.util.List;

public interface WeatherCallback_24H {
    void onFailed(int ErrorCode);
    void onSuccess(List<WHourly24Bean> beanList);
}
