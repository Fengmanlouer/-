package com.franz.easyweather.ui;

import android.app.AlertDialog;
import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.franz.easyweather.R;

public class MoreSetActivity extends AppCompatActivity implements View.OnClickListener{
    TextView moreSet_version,moreSet_delete,moreSet_share,moreSet_notice,moreSet_aboutWeather,moreSet_otherSet;
    ImageView moreSet_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_set);
        moreSet_back = findViewById(R.id.more_set_back);
        moreSet_version = findViewById(R.id.moreset_version);
        moreSet_delete = findViewById(R.id.more_set_delete);
        moreSet_share = findViewById(R.id.more_set_share);
        moreSet_notice = findViewById(R.id.more_set_notice);
        moreSet_aboutWeather = findViewById(R.id.more_set_aboutweather);
        moreSet_otherSet = findViewById(R.id.more_set_otherset);
        moreSet_back.setOnClickListener(this);
        moreSet_share.setOnClickListener(this);

        //String versionName = getVersionName();
        //versionTv.setText("当前版本:    v"+versionName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_set_back:
                finish();
                break;
            case R.id.more_set_delete:
                clearCache();
                break;
            case R.id.more_set_share:
                shareSoftwareMsg("蓝天气app是一款界面精美的天气预报软件，画面简约，播报天气情况非常精准，快来下载吧！");
                break;
        }

    }

    private void shareSoftwareMsg(String s) {
        /* 分享软件的函数*/
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"蓝天气"));
    }

    private void clearCache() {
        //************** 清除缓存的函数****************没有完全实现
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("确定删除所有缓存？");
    }

/*获取应用的版本名称
    public String getVersionName() {
        PackageManager manager = getPackageManager();
        String versionName = null;
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }*/
}
