package com.franz.easyweather.utils

import android.os.Message
import android.text.TextUtils
import android.util.Log
import com.franz.easyweather.MainActivity
import com.franz.easyweather.bean.WFuture7Bean
import com.franz.easyweather.bean.WHourly24Bean
import com.franz.easyweather.bean.WRealTimeBean
import com.franz.easyweather.callback.HttpCallback
import com.franz.easyweather.callback.WeatherCallback_24H
import com.franz.easyweather.callback.WeatherCallback_7D
import com.franz.easyweather.callback.WeatherCallback_now
import org.json.JSONException
import org.json.JSONObject

object JsonUtils {
    const val TAG : String = "MainActivityLog"
    /**
     * 获取当前天气情况
     */
     fun getNowWeather(location: String,callback: WeatherCallback_now) {
        val url = HttpUtils.getInstance().getNowUrl(location)
        Log.d(TAG, "url = $url")
        HttpUtils.getInstance().Post(url, object : HttpCallback {
            override fun onFailed(ErrorCode: Int) {
                callback.onFailed(ErrorCode)
            }
            override fun onResponse(JSONData: String) {
                if (TextUtils.isEmpty(JSONData)) {
                    callback.onFailed(0)
                    return
                }
                Log.d(TAG, "now = $JSONData")
                try {
                    val jsonObject = JSONObject(JSONData)
                    val jsonArray = jsonObject.getJSONArray("results")
                    val now = jsonArray.getJSONObject(0).getJSONObject("now")
                    val time : String = jsonArray.getJSONObject(0).getString("last_update");
                    val bean : WRealTimeBean? = HttpUtils.getInstance().fromJson(
                        now.toString(),
                        WRealTimeBean::class.java
                    )

                    if (bean != null) {
                        bean.last_update = time
                        bean.location = location;
                    }
                    callback.onSuccess(bean)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    /**
     * 获取未来七天天气情况（包括今天）
     */
     fun getFuture7Weather(location: String,callback: WeatherCallback_7D) {
        val url = HttpUtils.getInstance().getFuture7Url(location, 7)
        Log.d(TAG, "url = $url")
        HttpUtils.getInstance().Post(url, object : HttpCallback {
            override fun onFailed(ErrorCode: Int) {
                callback.onFailed(ErrorCode)
            }
            override fun onResponse(JSONData: String) {
                if (TextUtils.isEmpty(JSONData)) {
                    callback.onFailed(0)
                    return
                }
                Log.d(TAG, "future7 = $JSONData")
                try {
                    val jsonObject = JSONObject(JSONData)
                    val jsonArray = jsonObject.getJSONArray("results")
                    val future7 = jsonArray.getJSONObject(0).getJSONArray("daily")
                    val bean: MutableList<WFuture7Bean>? = HttpUtils.getInstance().fromListJson(
                        future7.toString(),
                        WFuture7Bean::class.java
                    )
                    callback.onSuccess(bean)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    /**
     * 获取24小时天气情况
     */
    fun getHourly24Weather(location: String,callback: WeatherCallback_24H) {
        val url = HttpUtils.getInstance().getHourly24Url(location, 24)
        Log.d(TAG, "url = $url")
        HttpUtils.getInstance().Post(url, object : HttpCallback {
            override fun onFailed(ErrorCode: Int) {
                callback.onFailed(ErrorCode)
            }
            override fun onResponse(JSONData: String) {
                if (TextUtils.isEmpty(JSONData)) {
                    callback.onFailed(0)
                    return
                }
                Log.d(TAG, "hourlv24 = $JSONData")
                try {
                    val jsonObject = JSONObject(JSONData)
                    val jsonArray = jsonObject.getJSONArray("results")
                    val hourly24 = jsonArray.getJSONObject(0).getJSONArray("hourly")
                    val bean: MutableList<WHourly24Bean>? = HttpUtils.getInstance().fromListJson(
                        hourly24.toString(),
                        WHourly24Bean::class.java
                    )
                    callback.onSuccess(bean)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}