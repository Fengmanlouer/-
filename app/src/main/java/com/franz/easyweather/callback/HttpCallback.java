package com.franz.easyweather.callback;

public interface HttpCallback {
    void onFailed(int ErrorCode);
    void onResponse(String JSONData);
}
