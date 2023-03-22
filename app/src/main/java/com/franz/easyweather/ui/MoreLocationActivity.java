package com.franz.easyweather.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.franz.easyweather.R;
import com.franz.easyweather.adapter.LocationAdapter;
import com.franz.easyweather.bean.LocationBusBean;
import com.franz.easyweather.bean.PageBean;
import com.franz.easyweather.bean.WRealTimeBean;
import com.franz.easyweather.callback.WeatherCallback_now;
import com.franz.easyweather.databinding.ActivityMoreLocationBinding;
import com.franz.easyweather.sql.Dao;
import com.franz.easyweather.utils.JsonUtils;
import com.franz.easyweather.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MoreLocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ActivityMoreLocationBinding binding;

    private static final String TAG = "MoreLocationActivityLog";

    private String location;
    private Dao dao;
    private LocationAdapter adapter;
    private List<WRealTimeBean> beanList = new ArrayList<>();
    private boolean deleteMode = false;

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    getWeatherData();
                    break;
                case 2:
                    /**
                     * 因为子线程添加数据不是同步，导致数据位置发生改变，所以将本地位置移到第一位*/
                    for (int i = 0; i < beanList.size(); i++) {
                        if (beanList.get(i).getLocation().equals(location)) {
                            WRealTimeBean bean = beanList.get(i);
                            beanList.remove(i);
                            beanList.add(0,bean);
                            break;
                        }
                    }

                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        binding = ActivityMoreLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWeatherIntent();
        initWeatherAdapter();
        getWeatherData();
        initSearchViewStyle();
        initListView();
    }

    private void getWeatherIntent() {
        Intent intent = getIntent();
        location = intent.getStringExtra("currentLocation");
    }

    private void initWeatherAdapter() {
        binding.weatherRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LocationAdapter(beanList);
        binding.weatherRecycler.setAdapter(adapter);

        adapter.setItemClickListener(new LocationAdapter.OnWeatherItemsClickListener() {
            @Override
            public void onClickListener(int pos,String location) {
                EventBus.getDefault().postSticky(new PageBean(pos,location));
                finish();
            }
        });

        adapter.setDelItemClickListener(new LocationAdapter.OnWeatherItemsClickListener() {
            @Override
            public void onClickListener(int pos, String location) {
                beanList.remove(pos);
                dao.Delete(location);
                adapter.notifyDataSetChanged();

                EventBus.getDefault().postSticky(new LocationBusBean(location, pos));
            }
        });

        binding.editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter == null)return;
                if (!deleteMode){
                    adapter.setDelete(true);
                    deleteMode = true;
                }else {
                    adapter.setDelete(false);
                    deleteMode = false;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 获取所以数据库中城市的天气*/
    private void getWeatherData() {
        if (dao == null) {
            dao = new Dao(this);
        }

        beanList.clear();//防止数据重复

        List<String> locationList = dao.QueryAll();
        if (locationList == null || locationList.size() == 0) {
            locationList = new ArrayList<>();
        }

        if (locationList.size() > 0) {
            String data = locationList.get(0);
            if (!data.equals(location)) {
                locationList.add(0, location);
            }
        }else {
            locationList.add(location);
        }

        for (int i = 0; i < locationList.size(); i++) {
            postWeather(locationList.get(i), locationList.size());
        }
    }


    /**
     * 获取实况天气*/
    private void postWeather(String location, int size) {
        JsonUtils.INSTANCE.getNowWeather(location, new WeatherCallback_now() {
            @Override
            public void onFailed(int ErrorCode) {

            }

            @Override
            public void onSuccess(WRealTimeBean bean) {
                if (bean != null) {
                    beanList.add(bean);
                    if (beanList.size() == size) {
                        handler.sendEmptyMessage(2);
                        return;
                    }
                }
            }
        });
    }

    /**
     * 更改searchView字体颜色
     */
    private void initSearchViewStyle() {
        EditText editText = (EditText) binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (editText != null) {
            editText.setTextColor(getColor(R.color.white));
            editText.setHintTextColor(getColor(R.color.searchHintColor));
            editText.setTextSize(14);

            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        } else {
            Log.d("MoreLocationActivity", "empty");
        }
    }

    private void initListView() {
        String[] cityArray = getResources().getStringArray(R.array.city);
        binding.locationList.setAdapter(new ArrayAdapter(this, R.layout.searchview_item, cityArray));
        /**
         * 属性为true表示listview获得当前焦点的时候，与相应用户输入的匹配符进行比对，筛选出匹配的ListView的列表中的项*/
        binding.locationList.setTextFilterEnabled(true);
        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.setSubmitButtonEnabled(false);

        binding.locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = adapterView.getAdapter().getItem(i).toString();
                if (dao == null) {
                    dao = new Dao(MoreLocationActivity.this);
                }
                boolean flag = dao.Query(str);
                if (flag) {
                    Toast.makeText(MoreLocationActivity.this, "该城市已添加到天气列表，请勿重复添加", Toast.LENGTH_SHORT).show();
                } else {
                    dao.Insert(str);
                    EventBus.getDefault().postSticky(new LocationBusBean(str, -1));
                    handler.sendEmptyMessage(1);
                    Toast.makeText(MoreLocationActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                binding.locationList.clearTextFilter();
                binding.locationList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            binding.locationList.clearTextFilter();
            binding.locationList.setVisibility(View.GONE);
        } else {
            binding.locationList.setVisibility(View.VISIBLE);
            binding.locationList.setFilterText(newText);
            //隐藏黑框
            binding.locationList.dispatchDisplayHint(View.INVISIBLE);
        }
        return true;
    }


}