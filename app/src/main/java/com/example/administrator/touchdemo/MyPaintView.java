package com.example.administrator.touchdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class MyPaintView extends View {
    private List<Point> allPoint = new ArrayList<Point>();

    public MyPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //Log.d("liu","liu");
        super.setOnTouchListener(new OnTouchListenerimpl());
    }

   //Touch Listener
    private class OnTouchListenerimpl implements OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Log.d("liu","liu onTouch onTouch  onTouch  onTouch");
            Point point = new Point((int)event.getX(),(int)event.getY());
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                allPoint.add(point);
            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                allPoint.add(point);
                MyPaintView.this.postInvalidate();
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                allPoint.add(point);
                MyPaintView.this.postInvalidate();
            }
            return true;
        }
    }

    //Draw
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        Paint myPaint = new Paint();
        myPaint.setColor(Color.RED);
        if(this.allPoint.size() > 0){
            Iterator<Point> iterators = allPoint.iterator();
            Point firstPoint = null;
            Point lastPoint = null;
            while (iterators.hasNext()){
                if(firstPoint == null){
                    firstPoint = (Point) iterators.next();
                }else if(lastPoint != null){
                    firstPoint = lastPoint;
                }
                lastPoint = (Point)iterators.next();
                canvas.drawLine(firstPoint.x,firstPoint.y,lastPoint.x,lastPoint.y,myPaint);
            }
        }
    }

}
