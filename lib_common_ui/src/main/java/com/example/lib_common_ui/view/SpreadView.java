package com.example.lib_common_ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.lib_common_ui.R;

import java.util.ArrayList;
import java.util.List;

public class SpreadView extends View {

    private Paint centerPaint;//中心圆Paint
    private int radius = 100;//中心圆半径
    private Paint spreadPaint;//扩散圆Paint
    private float centerX;//圆心x
    private float centerY;//圆心y
    private int distance = 5;//每次圆递增间距
    private int maxRadius = 80;//最大圆半径
    private int delayMiliseconds = 33;//扩散延迟间距，越大扩散越慢
    private List<Integer> spreadRadius = new ArrayList<>();//扩散圆层级数，元素为扩散的距离
    private List<Integer> alphas = new ArrayList<>();//对应每层圆的透明度


    public SpreadView(Context context) {
        this(context,null,0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpreadView,
                defStyleAttr,0);
        //中心圆半径
        radius = typedArray.getInt(R.styleable.SpreadView_spread_radius,radius);
        //扩散圆最大半径
        maxRadius = typedArray.getInt(R.styleable.SpreadView_spread_max_radius,maxRadius);
        //中心圆颜色
        int centerColor = typedArray.getColor(R.styleable.SpreadView_spread_center_color,
                ContextCompat.getColor(context,android.R.color.holo_red_dark));
        //扩散圆颜色
        int spreadColor = typedArray.getColor(R.styleable.SpreadView_spread_spread_color,
                ContextCompat.getColor(context,R.color.color_F71816));
        distance = typedArray.getInt(R.styleable.SpreadView_spread_distance,distance);
        typedArray.recycle();

        centerPaint = new Paint();
        centerPaint.setColor(centerColor);
        centerPaint.setAntiAlias(true);//打开抗锯齿
        //初始化不透明且扩散度为0
        alphas.add(255);
        spreadRadius.add(0);

        spreadPaint = new Paint();
        spreadPaint.setColor(spreadColor);
        spreadPaint.setAntiAlias(true);
        spreadPaint.setStyle(Paint.Style.STROKE);
        spreadPaint.setAlpha(255);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制当前所有的圆
        for(int i= 0;i < spreadRadius.size(); i++){
            int alpha = alphas.get(i);
            spreadPaint.setAlpha(alpha);
            int width = spreadRadius.get(i);
            //绘制一个spread圆
            canvas.drawCircle(centerX,centerY,width + radius,spreadPaint);

            //更新当前圆的透明度和半径
            if(alpha > 0 && width < 300){
                alpha = alpha - distance > 0 ? alpha - distance : 1;
                alphas.add(alpha);
                spreadRadius.set(i,width + distance);
            }

            //重置
            if(spreadRadius.get(spreadRadius.size() - 1) > maxRadius){
                alphas.add(255);
                spreadRadius.add(0);
            }

            //移除多余的圆
            if(spreadRadius.size() >= 8){
                alphas.remove(0);
                spreadRadius.remove(0);//移除掉最先扩散的圆
            }
            canvas.drawCircle(centerX,centerY,radius,centerPaint);
            postInvalidateDelayed(delayMiliseconds);
       }
    }
}
