package com.franz.easyweather.callback;

import com.franz.easyweather.bean.WRealTimeBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.weather.WeatherNowBean;

public interface WeatherCallback_now {
    void onFailed(int ErrorCode);
    void onSuccess(WRealTimeBean bean);
}
