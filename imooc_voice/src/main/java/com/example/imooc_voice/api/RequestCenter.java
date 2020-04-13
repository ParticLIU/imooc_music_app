package com.example.imooc_voice.api;

import com.example.imooc_voice.util.data.user.User;
import com.example.lib_network.CommonOkHttpClient;
import com.example.lib_network.listener.DisposeDataHandle;
import com.example.lib_network.listener.DisposeDataListener;
import com.example.lib_network.request.CommonRequest;
import com.example.lib_network.request.RequestParams;

public class RequestCenter {

    //根据参数发送Get请求
    public static void getRequest(String url, RequestParams params,
                                  DisposeDataListener listener,Class<?>clazz){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params)
                ,new DisposeDataHandle(listener,clazz));
    }

    //根据参数发送Post请求
    public static void postRequest(String url, RequestParams params,
                                   DisposeDataListener listener,Class<?>clazz){
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url,params)
                ,new DisposeDataHandle(listener,clazz));
    }

    //登陆请求
    public static void login(DisposeDataListener listener){
        RequestParams params = new RequestParams();
        params.put("mb", "18734924592");
        params.put("pwd", "999999q");
        RequestCenter.getRequest(UrlStore.Login,params,listener, User.class);
    }
}
