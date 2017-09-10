package com.example.administrator.touchdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/9/9.
 */

public class CustomProgress extends View {
    public CustomProgress(Context context){
        this(context,null);
    }
    public CustomProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    private Paint mPaint ;
    private Path mPath = new Path();
    private int mCircleWidth = 3;
    private float mX,mY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
     /*  // super.onDraw(canvas);
        mPaint.setAntiAlias(false);
       // mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(Color.CYAN);

        *//**
         * 这是一个居中的圆
         *//*
        float x = (getWidth() - getHeight() / 2) / 2;
        float y = getHeight() / 4;


        RectF oval = new RectF( x, y, getWidth() - x, getHeight() - y);
        canvas.drawArc(oval,0,300,true,mPaint);*/

/*        mPath.moveTo(100, 100);
        mPath.lineTo(110, 110);
        mPath.lineTo(115, 115);
        mPath.moveTo(120,120);
        mPath.lineTo(125,125);
        mPath.lineTo(130,130);
        mPath.lineTo(135,135);
        canvas.drawPath(mPath, mPaint);*/

/*        mPath.reset();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);


        mPath.moveTo(100,600);
        mPath.quadTo(300,300,600,600);
        canvas.drawPath(mPath, mPaint);*/

/*        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(0, 300);
        path.quadTo(150, 0, 300, 300);
        canvas.drawPath(path, mPaint);*/

/*        canvas.drawText("画圆：", 10, 20, mPaint);// 画文本
        canvas.drawCircle(60, 20, 10, mPaint);// 小圆
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(120, 20, 20, mPaint);// 大圆*/


/*        //画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 10, 310, mPaint);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, mPaint);//画出贝塞尔曲线*/

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
        }

        invalidate();
        return true;
    }

    private void touchDown(MotionEvent event){
        Log.d("liu","liu touchDown  11111111111111111");
        mPath.reset();
        final float x = event.getX();
        final float y = event.getY();
        mX = x;
        mY = y;
        mPath.moveTo(x, y);
    }

    private void touchMove(MotionEvent event){
        final float x = event.getX();
        final float y = event.getY();
        float preX = mX;
        float preY = mY;
        float dx = Math.abs(x-preX);
        float dy = Math.abs(y-preY);
        if (dx > 3 || dy > 3){
            float cx = (mX+preX)/2;
            float cy = (mY+preY)/2;
            mPath.quadTo(preX,preY,mX,mY);
            mX = x;
            mY = y;
        }
    };
}
