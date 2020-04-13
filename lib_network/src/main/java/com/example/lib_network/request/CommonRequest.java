package com.example.lib_network.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author xiaoyu.liu
 * create at 2020/4/2
 * description: 对外提供get/post/文件上传请求
 */
public class CommonRequest {

    /**
     * @author xiaoyu.liu
     * create at 2020/4/2
     * description: 对外创建post请求-不带请求头
     */
    public static Request createPostRequest(String url,RequestParams params){
        return createPostRequest(url, params,null);
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/2
     * description: 对外创建post请求-带请求头
     */
    public static Request createPostRequest(String url,RequestParams params,RequestParams headers){
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if(params != null){
            for(Map.Entry<String,String> entry:params.urlParams.entrySet()){
                //参数遍历
                mFormBodyBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        Headers.Builder mHeaderBuilder = new Headers.Builder();
        if(headers != null){
            for(Map.Entry<String,String> entry:headers.urlParams.entrySet()){
                //请求头遍历
                mHeaderBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .headers(mHeaderBuilder.build())
                .post(mFormBodyBuilder.build())
                .build();
        return request;
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/2
     * description: 对外创建一个Get请求-不带请求头
     */
    public static Request createGetRequest(String url,RequestParams params){
        return createGetRequest(url, params,null);
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/2
     * description: 对外创建一个Get请求-带请求头
     */
    public static Request createGetRequest(String url,RequestParams params,RequestParams headers){
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if(params != null){
            for(Map.Entry<String,String> entry:params.urlParams.entrySet()){
                //参数遍历
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        Headers.Builder mHeaderBuilder = new Headers.Builder();
        if(headers != null){
            for(Map.Entry<String,String> entry:headers.urlParams.entrySet()){
                //请求头遍历
                mHeaderBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(urlBuilder.substring(0,urlBuilder.length()-1))
                .headers(mHeaderBuilder.build())
                .get()
                .build();
        return request;
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/2
     * description: 文件上传请求
     */
    public static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    public static Request createMultiPostRequest(String url,RequestParams params){
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for(Map.Entry<String,Object> entry:params.fileParams.entrySet()){
                if(entry.getValue() instanceof File){
                    requestBody.addPart(Headers.of("content-Disposition","form-data;name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE,(File)entry.getValue()));
                }else if(entry.getValue() instanceof String){
                    requestBody.addPart(Headers.of("content-Disposition","form-data;name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null,(String)entry.getValue()));
                }
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
        return request;
    }
}
