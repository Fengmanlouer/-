package com.franz.easyweather.callback;

import com.franz.easyweather.bean.WFuture7Bean;
import com.franz.easyweather.bean.WRealTimeBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;

import java.util.List;

public interface WeatherCallback_7D {
    void onFailed(int ErrorCode);
    void onSuccess(List<WFuture7Bean> beanList);
}
