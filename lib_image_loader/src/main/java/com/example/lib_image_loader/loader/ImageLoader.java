package com.example.lib_image_loader.loader;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.lib_image_loader.listener.CustomerRequestListener;
import com.example.lib_image_loader.util.BlurUtil;
import com.example.lib_image_loader.util.DensityUtil;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

/**
 * @author xiaoyu.liu
 * create at 2020/4/7
 * description: 图片加载类，外界唯一调用类，
 * 支持为View,Notification,APPWedget加载图片
 */
public class ImageLoader implements ILoader{
    private volatile static ImageLoader singleton = null;
    private Handler uiHandler;
    
    public static ImageLoader getInstance(){
            if(singleton == null){
                synchronized (ImageLoader.class){
                    if(singleton == null){
                        singleton = new ImageLoader();
                    }
                }
            }
            return singleton;
        }
    
    private ImageLoader(){
        uiHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/8
     * description: 加载图片
     */
    @Override
    public void loadImage(String url, ImageView imageView, int errorres) {
        Context context = imageView.getContext();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(initOptions(errorres))
                .transition(withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadImage(String url, ImageView imageView, int errorres, CustomerRequestListener listener) {
        Context context = imageView.getContext();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(initOptions(errorres))
                .transition(withCrossFade())
                .listener(listener)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, RemoteViews rv, int imageViewId, Notification notification,
                          int NOTIFICATION_ID, String url,int errorres) {
        NotificationTarget notificationTarget = new NotificationTarget(context,imageViewId,rv,notification,NOTIFICATION_ID);
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(initOptions(errorres))
                .transition(withCrossFade())
                .fitCenter()
                .into(notificationTarget);
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/8
     * description: 加载详情图片
     */
    @Override
    public void loadImage(final String url, ViewGroup viewGroup, final ImageView imageView, final int errorres) {
        final Context context = viewGroup.getContext();
        //动态设置控件宽高
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //图片的宽度，高度
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        //设备的宽度
                        int equipWidth = DensityUtil.getScreenWidth(context);
                        int image_height = (equipWidth * height) / width;
                        //设置控件的宽高
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.width = equipWidth;
                        params.height = image_height;
                        imageView.setLayoutParams(params);
                        //设置图片
                        loadImageBySize(url,imageView,params.width,params.height,errorres);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/8
     * description: 加载圆形图片
     */
    @Override
    public void loadCircleImage(String url, final ImageView imageView, int errorres) {
        Context context = imageView.getContext();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(initOptions(errorres))
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(imageView.getResources(),resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    /**
     * @author xiaoyu.liu
     * create at 2020/4/8
     * description: 加载高斯模糊效果图片
     */
    @Override
    public void loadBlurImage(String url, final ImageView imageView, int errorres) {
        Context context = imageView.getContext();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(initOptions(errorres))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Drawable drawable = new BitmapDrawable(
                                        BlurUtil.doBlur(resource,100,true));
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageDrawable(drawable);
                                    }
                                });
                            }
                        }).start();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    //根据图片宽高加载图片
    private void loadImageBySize(String url, final ImageView imageView,int width,int height, int errorres){
        Context context = imageView.getContext();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(initOptions(width, height, errorres))
                .transition(withCrossFade())
                .into(imageView);
    }

    //图片配置
    @SuppressLint("CheckResult")
    private RequestOptions initOptions(int errorres){
        RequestOptions options = new RequestOptions();
        options.placeholder(errorres)
                .error(errorres)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.NORMAL);
        return options;
    }

    //图片配置
    @SuppressLint("CheckResult")
    private RequestOptions initOptions(int width, int height, int errorres){
        RequestOptions options = new RequestOptions();
        options .override(width,height)
                .placeholder(errorres)
                .error(errorres)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.NORMAL);
        return options;
    }
}
