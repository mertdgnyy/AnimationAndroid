package com.example.animationandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;


public class Draw extends View {

    Bitmap bu;
    int y;
    int max = 800;
    float xx;

    public Draw(Context context) {
        super(context);
        bu = BitmapFactory.decodeResource(getResources(),R.drawable.bu1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Rect rect = new Rect();
        rect.set(0,0, canvas.getWidth(), canvas.getHeight());

        Paint color = new Paint();
        color.setColor(Color.BLACK);
        color.setStyle(Paint.Style.FILL);

        canvas.drawRect(rect,color);

        if(y < canvas.getHeight()) {
            y += 20;
        }
        else{
            y = 0;
            Random rand = new Random();
            xx = rand.nextFloat() * max;
        }


        Paint paint = new Paint();
        canvas.drawBitmap(bu, xx, y, paint);
        invalidate();

    }

}
