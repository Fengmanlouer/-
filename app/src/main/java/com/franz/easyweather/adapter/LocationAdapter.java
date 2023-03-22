package com.franz.easyweather.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.franz.easyweather.R;
import com.franz.easyweather.base.BaseApplication;
import com.franz.easyweather.bean.LocationBean;
import com.franz.easyweather.bean.WFuture7Bean;
import com.franz.easyweather.bean.WRealTimeBean;
import com.franz.easyweather.utils.WeatherIconUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> implements View.OnClickListener{
    private List<WRealTimeBean> beanList;
    private OnWeatherItemsClickListener listener;
    private OnWeatherItemsClickListener delListener;
    private boolean isDelete = false;
    public LocationAdapter(List<WRealTimeBean> beanList){
        this.beanList = beanList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_weather_simple,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (beanList == null || beanList.size() == 0)return;

        WRealTimeBean bean = beanList.get(position);
        if (position == 0){
            holder.location.setText("我的位置");
        }else {
            holder.location.setText(bean.getLocation());
        }
        holder.temp.setText(bean.getTemperature()+"°");
        holder.status.setText(bean.getText());
        holder.time.setText(bean.getLast_update());

        switch (bean.getText()){
            case "晴":holder.img.setImageDrawable(BaseApplication.context.getDrawable(R.drawable.icon_bg_sunny));break;
            case "多云":holder.img.setImageDrawable(BaseApplication.context.getDrawable(R.drawable.icon_bg_cloudy));break;
            case "阴":holder.img.setImageDrawable(BaseApplication.context.getDrawable(R.drawable.icon_bg_cloudy));break;
            case "雨":holder.img.setImageDrawable(BaseApplication.context.getDrawable(R.drawable.icon_bg_big_rain));break;
            case "雪":holder.img.setImageDrawable(BaseApplication.context.getDrawable(R.drawable.icon_bg_snow));break;
            case "雷":holder.img.setImageDrawable(BaseApplication.context.getDrawable(R.drawable.icon_bg_thunder));break;
        }

        if (isDelete && position != 0){
            holder.delete.setVisibility(View.VISIBLE);
        }else {
            holder.delete.setVisibility(View.GONE);
        }

        holder.layout.setTag(position);
        holder.delete.setTag(position);

    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    @Override
    public void onClick(View view) {
        if (listener == null || delListener == null)return;
        int tag = (int)view.getTag();

        String location = beanList.get(tag).getLocation();
        switch (view.getId()){
            case R.id.simpleLayout:
                listener.onClickListener(tag,location);
                break;
            case R.id.simpleDelete:
                delListener.onClickListener(tag,location);
                break;
        }
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView time,status,location,temp;
        private ImageView img,delete;
        private ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.simpleTime);
            status = itemView.findViewById(R.id.simpleStatus);
            location = itemView.findViewById(R.id.simpleLocation);
            temp = itemView.findViewById(R.id.simpleTemp);
            img = itemView.findViewById(R.id.simpleImg);
            layout = itemView.findViewById(R.id.simpleLayout);
            delete = itemView.findViewById(R.id.simpleDelete);
            delete.setOnClickListener(LocationAdapter.this);
            layout.setOnClickListener(LocationAdapter.this);
        }
    }
    public void setItemClickListener(OnWeatherItemsClickListener listener){
        this.listener = listener;
    }
    public void setDelItemClickListener(OnWeatherItemsClickListener listener){
        this.delListener = listener;
    }
    public interface OnWeatherItemsClickListener{
        void onClickListener(int pos,String location);
    }
}
