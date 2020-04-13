package com.example.lib_image_loader.loader;

import android.app.Notification;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.lib_image_loader.listener.CustomerRequestListener;

public interface ILoader {

    /**
     * 1.为ImageView加载图片
     * 2.加载圆形图片
     * 3.高斯模糊加载图片
     * 4.为notification加载图片
     * 5.加载大图,动态计算图片空间宽高
     */

    void loadImage(String url,ImageView imageView,int errorres);
    void loadImage(String url, ImageView imageView, int errorres,
                   CustomerRequestListener listener);
    void loadImage(Context context, RemoteViews rv, int imageViewId,
                   Notification notification,int NOTIFICATION_ID,String url,int errorres);
    void loadImage(String url, ViewGroup viewGroup,ImageView imageView, int errorres);
    void loadCircleImage(String url,ImageView imageView,int errorres);
    void loadBlurImage(String url,ImageView imageView,int errorres);
}
