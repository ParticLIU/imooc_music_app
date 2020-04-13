package com.example.lib_network;

import com.example.lib_network.listener.DisposeDataHandle;
import com.example.lib_network.response.CommonFileCallback;
import com.example.lib_network.response.CommonJsonCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    //完成对OkHttpClient的初始化
    static {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        //添加公共请求头
        okhttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Imooc-Mobile")
                        .build();// 标明发送本次请求的客户端
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //包含header、body数据
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(loggingInterceptor);
        okhttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);
        okhttpClientBuilder.followSslRedirects(true);
        mOkHttpClient = okhttpClientBuilder.build();
    }

    //get请求
    public static Call get(Request request, DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    //post请求
    public static Call post(Request request, DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    //文件下载
    public static Call downloadFile(Request request,DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }
}
