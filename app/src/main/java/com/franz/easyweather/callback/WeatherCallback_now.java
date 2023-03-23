package com.franz.easyweather.callback;

import com.franz.easyweather.bean.WRealTimeBean;

public interface WeatherCallback_now {
    void onFailed(int ErrorCode);
    void onSuccess(WRealTimeBean bean);
}
