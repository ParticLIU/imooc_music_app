package com.example.imooc_voice.ui.home.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imooc_voice.R;
import com.example.imooc_voice.model.CHANNEL;
import com.example.imooc_voice.model.login.LoginEvent;
import com.example.imooc_voice.ui.home.adapter.HomePagerAdapter;
import com.example.imooc_voice.ui.login.activity.LoginActivity;
import com.example.imooc_voice.util.Logger;
import com.example.imooc_voice.util.data.user.UserManager;
import com.example.imooc_voice.view.magicIndicator.ScaleTransitionPagerTitleView;
import com.example.lib_common_ui.base.BaseActivity;
import com.example.lib_image_loader.loader.ImageLoader;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeMainActivity extends BaseActivity {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.avatr_view)
    ImageView avatrView;
    @BindView(R.id.unloggin_layout)
    LinearLayout unlogginLayout;

    private static final CHANNEL[] CHANNELS = new CHANNEL[]{CHANNEL.MY
            , CHANNEL.DISCOVERY, CHANNEL.FRIEND};

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        unlogginLayout.setVisibility(View.GONE);
        avatrView.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().loadCircleImage(UserManager.getInstance().getUser().data.photoUrl
        ,avatrView,R.drawable.loading_01);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        init();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        initViewPager();
        initMagicIndicator();
    }

    //初始化ViewPager
    private void initViewPager() {
        HomePagerAdapter pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),
                CHANNELS);
        viewPager.setAdapter(pagerAdapter);
    }

    //初始化ViewPager指示器
    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                Logger.e(Logger.INFO.HOMEMAIN, "channels长度：" + CHANNELS.length);
                return CHANNELS == null ? 0 : CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(CHANNELS[index].getKey());
                simplePagerTitleView.setTextSize(19);
                simplePagerTitleView.setTypeface(Typeface.DEFAULT_BOLD);
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.toggle_view, R.id.search_view, R.id.unloggin_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toggle_view:
                toggle();
                break;
            case R.id.search_view:
                break;
            case R.id.unloggin_layout:
                if (!UserManager.getInstance().hasLogin()) {
                    LoginActivity.start(this, null);
                } else {
                    //关闭侧滑
                    toggle();
                }
                break;
        }
    }

    //左侧抽屉事件
    private void toggle() {
        int drawerLockMode = drawerLayout.getDrawerLockMode(GravityCompat.START);
        if (drawerLayout.isDrawerVisible(GravityCompat.START)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
