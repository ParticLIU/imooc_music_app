package com.example.lib_common_ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.lib_common_ui.util.StatusBarUtil;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
    }

    //初始化控件相关
    protected abstract void initView();

    //初始化数据相关
    protected abstract void init();

    //初始化事件相关
    protected abstract void initEvent();



}
