package com.example.lib_network.listener;

/**
 * @author xiaoyu.liu
 * create at 2020/4/2
 * description: 业务层真正处理的地方
 */
public interface DisposeDataListener {

    //请求成功回调事件处理
    void onSuccess(Object responseObj);

    //请求失败回调事件处理
    void onFailure(Object reasonObj);
}
