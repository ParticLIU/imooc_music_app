package com.example.imooc_voice.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.imooc_voice.R;
import com.example.imooc_voice.api.RequestCenter;
import com.example.imooc_voice.model.login.LoginEvent;
import com.example.imooc_voice.util.data.user.User;
import com.example.imooc_voice.util.data.user.UserManager;
import com.example.lib_common_ui.base.BaseActivity;
import com.example.lib_network.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        init();
        initEvent();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.login_view)
    public void onViewClicked() {
        RequestCenter.login(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                //处理正常逻辑
                User user = (User) responseObj;
                Log.e("login:", "onSuccess: " + user.toString() );
                UserManager.getInstance().saveUser(user);
                EventBus.getDefault().post(new LoginEvent());
                finish();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }
}
