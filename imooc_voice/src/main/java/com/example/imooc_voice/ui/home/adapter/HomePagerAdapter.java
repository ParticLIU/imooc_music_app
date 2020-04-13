package com.example.imooc_voice.ui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.imooc_voice.model.CHANNEL;
import com.example.imooc_voice.ui.home.fragment.DiscoveryFragment;
import com.example.imooc_voice.ui.home.fragment.FriendFragment;
import com.example.imooc_voice.ui.home.fragment.MineFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private CHANNEL[] mList;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] datas) {
        super(fm);
        this.mList = datas;
    }

    @Override
    public Fragment getItem(int position) {
        int value = mList[position].getValue();
        switch (value) {
            case CHANNEL.VALUE_MY:
                return MineFragment.newInstance();
            case CHANNEL.VALUE_DISCOVERY:
                return DiscoveryFragment.newInstance();
            case CHANNEL.VALUE_FRIEND:
                return FriendFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }
}
