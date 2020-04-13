package com.example.lib_network.response;

import android.os.Handler;
import android.os.Looper;

import com.example.lib_network.exception.OkHttpException;
import com.example.lib_network.listener.DisposeDataHandle;
import com.example.lib_network.listener.DisposeDataListener;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonJsonCallback implements Callback {

    protected final String EMPTY_MSG = "";

    //the java layer exception,do not same to the logic error
    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private DisposeDataListener mListener;
    private Class<?> mClass;
    private Handler mDeliveryHandler;

    public CommonJsonCallback(DisposeDataHandle handle){
        mListener = handle.mListemer;
        mClass = handle.mClass;
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result){
        if(result == null || result.trim().equals("")){
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
            return;
        }

        try {
            if(mClass == null){
                mListener.onSuccess(result);
            }else {
                Gson gson = new Gson();
                Object object = gson.fromJson(result,mClass);
                if(object != null){
                    mListener.onSuccess(object);
                }else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                }
            }
        }catch (Exception e){
            mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
        }
    }
}
