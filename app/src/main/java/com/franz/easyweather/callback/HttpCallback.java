package com.franz.easyweather.callback;

import java.io.IOException;

public interface HttpCallback {
    void onFailed(int ErrorCode);
    void onResponse(String JSONData);
}
