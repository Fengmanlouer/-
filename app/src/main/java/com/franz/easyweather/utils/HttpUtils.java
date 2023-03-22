package com.franz.easyweather.utils;

import android.util.Log;

import com.franz.easyweather.callback.HttpCallback;
import com.franz.easyweather.param.ErrorCodeParam;
import com.franz.easyweather.param.WeatherParam;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static HttpUtils spInstant = null;
    private static String TAG = "MainActivityLog";

    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (spInstant == null) {
            Sync();
        }
        return spInstant;
    }

    private static synchronized void Sync() {
        if (spInstant == null) {
            spInstant = new HttpUtils();
        }
    }



    public void Post(String url, HttpCallback callback){
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailed(ErrorCodeParam.NetworkError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                if (code == 200){
                    String json = response.body().string();
                    callback.onResponse(json.toString());
                }else {
                    callback.onFailed(ErrorCodeParam.PostError);
                }
            }
        });
    }


    /**
     * 获取当前实况天气*/
    public String getNowUrl(String location) {
        HttpUrl.Builder builder = HttpUrl.parse(WeatherParam.NowWeatherURL).newBuilder();
        builder.addQueryParameter("key",WeatherParam.weatherToken);
        builder.addQueryParameter("location",location);

        return builder.build().toString();
    }

    /**
     * 获取未来七天天气预报情况*/
    public String getFuture7Url(String location,int days) {
        HttpUrl.Builder builder = HttpUrl.parse(WeatherParam.Future7WeatherURL).newBuilder();
        builder.addQueryParameter("key",WeatherParam.weatherToken);
        builder.addQueryParameter("location",location);
        builder.addQueryParameter("days",days+"");

        return builder.build().toString();
    }

    /**
     *  获取24小时天气情况*/
    public String getHourly24Url(String location,int hours) {
        HttpUrl.Builder builder = HttpUrl.parse(WeatherParam.Hourly24WeatherURL).newBuilder();
        builder.addQueryParameter("key",WeatherParam.weatherToken);
        builder.addQueryParameter("location",location);
        builder.addQueryParameter("hours",hours+"");

        return builder.build().toString();
    }


     /**
      * resolve bean*/
    public <T> T fromJson(String josn,Class<T> c){
        Gson gson = new Gson();
        T t = null;
        return t = gson.fromJson(josn,c);
    }

    /**
     * resolve list bean*/
    public <T> List<T> fromListJson(String json, Class<T> c){
        List<T> list  = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement element: array) {
            list.add(gson.fromJson(element,c));
        }
        return list;
    }

}
