package com.franz.easyweather.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.franz.easyweather.R;
import com.franz.easyweather.base.BaseApplication;
import com.franz.easyweather.bean.WFuture7Bean;
import com.franz.easyweather.utils.WeatherIconUtils;
import com.qweather.sdk.bean.weather.WeatherDailyBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Weather7DAdapter extends RecyclerView.Adapter<Weather7DAdapter.ViewHolder> {
    private List<WFuture7Bean> dailyBeanList;
    public Weather7DAdapter(List<WFuture7Bean> dailyBeanList){
        this.dailyBeanList = dailyBeanList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_weather7d,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dailyBeanList == null || dailyBeanList.size() == 0)return;

        WFuture7Bean bean = dailyBeanList.get(position);

        if (isToday(bean.getDate())) {
            holder.time.setText("今天");
        }else {
            holder.time.setText(getWeek(bean.getDate()));
        }
        boolean flag = isLight();
        String status = null;
        if (flag){
            //白天
             status = bean.getText_day();
        }else {
            //夜晚
             status = bean.getText_night();
        }

        int icon = WeatherIconUtils.getWeatherCode(status);
        holder.img.setImageResource(icon);

        holder.minTemp.setText(bean.getLow()+"°");
        holder.maxTemp.setText(bean.getHigh()+"°");
    }

    @Override
    public int getItemCount() {
        return dailyBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView time,minTemp,maxTemp;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_7d);
            minTemp = itemView.findViewById(R.id.minTemp_7d);
            maxTemp = itemView.findViewById(R.id.maxTemp_7d);
            img = itemView.findViewById(R.id.icon_7d);
        }
    }

    private boolean isLight(){
        Date date = new Date(System.currentTimeMillis());
        int hour = date.getHours();
        //白天是指上午8:00到晚上20:00的12小时,夜晚是指20:00到次日早上8:00的12小时
        if (hour >= 8 && hour < 20){
            return true;
        }
        return false;
    }

    private boolean isToday(String time){
        long curTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format.format(new Date(curTime));
       if (time.equals(newTime)){
           return true;
       }
       return false;
    }

    private String getWeek(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String week = null;
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case 1:week = "星期天";break;
            case 2:week = "星期一";break;
            case 3:week = "星期二";break;
            case 4:week = "星期三";break;
            case 5:week = "星期四";break;
            case 6:week = "星期五";break;
            case 7:week = "星期六";break;
            default:week = "未知";break;
        }
        return week;
    }
}
