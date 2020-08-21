package com.example.rectangle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyCanvas extends View {

    Paint paint1 = new Paint();
    Paint paint2 = new Paint();
    float canvasSize;
    float x1 = 100;
    float y1 = 100;
    float x2 = 400;
    float y2 = 300;
    float ddx1 ;
    float ddy1 ;
    float ddx2 ;
    float ddy2 ;
    float moveX;
    float moveY;
    float TOUCH_TOLERANCE = 40;
    float MOVE_TOLERANCE = 8;

    public MyCanvas(Context context) {
        super(context);
        init(null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        paint1.setColor(getResources().getColor(R.color.paint1));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint1.isAntiAlias();

        paint2.setColor(getResources().getColor(R.color.paint2));
        paint2.setStyle(Paint.Style.FILL);
        paint2.isAntiAlias();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasSize = canvas.getHeight();
        canvas.drawColor(getResources().getColor(R.color.canvasBg));
        canvas.drawRect(x1, y1, x2, y2, paint1);
        canvas.drawRect(x1, y1, x2, y2, paint2);
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:{
                continueDrawing(x, y);
            }
            case MotionEvent.ACTION_DOWN:{
                moveY = y;
                moveX = x;
                ddx1 = x - x1;
                ddy1 = y - y1;
                ddx2 = x - x2;
                ddy2 = y - y2;
            }
        }
        return true;
    }

    private void continueDrawing(float x, float y) {
        float dx1 = Math.abs(x - x1);
        float dy1 = Math.abs(y - y1);
        float dx2 = Math.abs(x - x2);
        float dy2 = Math.abs(y - y2);

        if (dx1 <= TOUCH_TOLERANCE && dy1 <= TOUCH_TOLERANCE) {
            x1 = x;
            y1 = y;
        }else if (dx2 <= TOUCH_TOLERANCE && dy2 <= TOUCH_TOLERANCE) {
            x2 = x;
            y2 = y;
        }else if (dx2 <= TOUCH_TOLERANCE && dy1 <= TOUCH_TOLERANCE) {
            x2 = x;
            y1 = y;
        }else if (dx1 <= TOUCH_TOLERANCE && dy2 <= TOUCH_TOLERANCE) {
            x1 = x;
            y2 = y;
        }else if(((x1>x2)?((x>x2)&&(x<x1)):((x<x2)&&(x>x1))) && ((y1>y2)?((y>y2)&&(y<y1)):((y<y2)&&(y>y1)))){
            if ((Math.abs(x - moveX)>MOVE_TOLERANCE)||(Math.abs(y - moveY)>MOVE_TOLERANCE)) {
//                x1 = x + (x-x1);
//                x2 = x + (x-x2);
//                y1 = y + (y-y1);
//                y2 = y + (y-y2);

                x1 = x - ddx1;
                x2 = x - ddx2;
                y1 = y - ddy1;
                y2 = y - ddy2;
                moveX = x;
                moveY = y;
            }
            //Toast.makeText(getContext(), "touched inside", Toast.LENGTH_SHORT).show();
        }
    }

}
