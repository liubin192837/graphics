package com.example.administrator.touchdemo;

import android.animation.ValueAnimator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/9/9.
 */

public class Bezier extends View {
    private int color,mWidth, mHeight, xSize, ySize;
    private int xWidth,yHeight,arcHeight;
    private Paint mPaint,textPaint;
    private Path mPath,arcPath;
    private Rect rect;
    private boolean isSucessful;
    private AnimatorSet animSet;
    private String text = "火箭发射成功!!!";
    public Bezier(Context context){
        this(context,null);
    }
    public Bezier(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public Bezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        mPaint = new Paint();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.MyBesselView,defStyleAttr,0);
        int n = typedArray.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.MyBesselView_titleColor:
                    // 默认颜色设置为黑色
                    color = typedArray.getColor(attr, Color.BLACK);
                    break;
            }
        }

        typedArray.recycle();
        init();
    }

    private void init(){
        mPaint = new Paint();
        arcPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);

        mPath = new Path();

        textPaint = new Paint();
        textPaint.setAntiAlias(true);

        rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        //width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的宽度的1/2
            width = widthSize*1/2;
        }

        //height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的高度的3/4
            height = heightSize*1/2;
        }

        mWidth = width;
        mHeight = height;

        xSize = mWidth*1/10;
        ySize = mHeight*1/8;

        xWidth = xSize;
        yHeight = ySize;

        arcHeight = mHeight*7/10;
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                isSucessful = false;
            case MotionEvent.ACTION_MOVE:
                xWidth = x;
                yHeight = y;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (yHeight > mHeight*7/10 && xWidth > mWidth *4/10 && xWidth < mWidth*6/10) {
                    startAnim();
                }
                break;
        }
        return true;
    }

    private void startAnim(){
        //动画实现
        ValueAnimator animator = ValueAnimator.ofInt(yHeight, -ySize);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yHeight = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animSet.setDuration(1200);
        animSet.play(animator);
        animSet.start();
        isSucessful = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //确定小火箭控制点的范围
        if (xWidth < xSize) {
            xWidth = xSize;
        }
        if (xWidth > mWidth*9/10) {
            xWidth = mWidth*9/10;
        }
        if (yHeight > mHeight*8/10) {
            yHeight = mHeight*8/10;
        }
        if (yHeight > mHeight*7/10 && xWidth < mWidth*4/10) {
            yHeight = mHeight*7/10;
        }
        if (yHeight > mHeight*7/10 && xWidth > mWidth*6/10) {
            yHeight = mHeight*7/10;
        }

        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.reset();

        //绘制小火箭
        mPath.moveTo(xWidth - xSize*1/2, yHeight - ySize*3/5);
        mPath.lineTo(xWidth, yHeight - ySize);
        mPath.lineTo(xWidth + xSize*1/2, yHeight - ySize*3/5);
        mPath.moveTo(xWidth - xSize * 1/4, yHeight - ySize*4/5);
        mPath.lineTo(xWidth - xSize * 1/4, yHeight);
        mPath.lineTo(xWidth + xSize * 1/4, yHeight);
        mPath.lineTo(xWidth + xSize * 1/4, yHeight - ySize*4/5);
        canvas.drawPath(mPath, mPaint);

        //绘制发射台
        mPaint.setStrokeWidth(10);
        arcPath.reset();
        arcPath.moveTo(mWidth * 1/10, mHeight *7/10);
        if (yHeight > mHeight * 7/10 && xWidth > mWidth * 4/10 && xWidth < mWidth *6/10) {
            arcHeight = yHeight + yHeight - mHeight * 7 / 10;
        } else {
            arcHeight = mHeight * 7/10;
        }
        arcPath.quadTo(mWidth * 5/10, arcHeight, mWidth * 9/10, mHeight * 7/10);
        canvas.drawPath(arcPath, mPaint);

        //绘制成功后的文字
        if (isSucessful && yHeight < 0) {
            textPaint.setTextSize(80);
            textPaint.setColor(color);
            textPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, mWidth * 1/2 - rect.width()/2, mHeight * 1/2 + rect.height() * 1/2, textPaint);
        }

    }
}
