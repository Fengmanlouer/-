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
import com.franz.easyweather.bean.WHourly24Bean;
import com.franz.easyweather.utils.WeatherIconUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Weather24HAdapter extends RecyclerView.Adapter<Weather24HAdapter.ViewHolder> {
    //加final
    private final List<WHourly24Bean> hourlyBeanList;
    public Weather24HAdapter(List<WHourly24Bean> hourlyBeanList){
        this.hourlyBeanList = hourlyBeanList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_weather24h,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (hourlyBeanList == null || hourlyBeanList.size() == 0)return;
        Log.d("MainActivityLog","pos = "+position);

        WHourly24Bean bean = hourlyBeanList.get(position);

        String hour = bean.getTime().substring(11,13);
        if (isNow(hour)){
            holder.time.setText("此时");
        }else {
            holder.time.setText(bean.getTime().substring(11,13)+"时");
        }

        int icon = WeatherIconUtils.getWeatherCode(bean.getText());
        holder.img.setImageResource(icon);
        holder.temp.setText(bean.getTemperature()+"°");
    }

    @Override
    public int getItemCount() {
        return hourlyBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        //加final
        private final TextView time,temp;
        //加final
        private final ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_24h);
            temp = itemView.findViewById(R.id.temp_24h);
            img = itemView.findViewById(R.id.icon_24h);
        }
    }

    private boolean isNow(String time){
        long curTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String newTime = format.format(new Date(curTime));
        if (time.equals(newTime)){
            return true;
        }
        return false;
    }
}
