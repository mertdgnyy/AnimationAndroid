package com.example.animationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity implements View.OnTouchListener {
    View view;
    Bitmap bu;
    float x,y;
    int max = 800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new View(this);
        view.setOnTouchListener(this);
        bu = BitmapFactory.decodeResource(getResources(),R.drawable.bu1);
        x = y = 0;
        setContentView(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.resume();
    }

    @Override
    public boolean onTouch(android.view.View v, MotionEvent event) {
        // touch event will be implemented
        return false;
    }


    public class View extends SurfaceView implements Runnable{

        boolean resume = false;
        Thread thread = null;
        SurfaceHolder sHolder;


        public View(Context context){
            super(context);
            sHolder = getHolder();
        }

        @Override
        public void run() {
            while(resume == true){
                if(sHolder.getSurface().isValid()) {

                    Canvas canvas = sHolder.lockCanvas();
                    Rect rect = new Rect();
                    rect.set(0, 0, canvas.getWidth(), canvas.getHeight());
                    if (y < canvas.getHeight()) {
                        y += 20;
                    } else {
                        y = 0;
                        Random rand = new Random();
                        x = rand.nextFloat() * max;
                    }
                    Paint color = new Paint();
                    color.setColor(Color.BLACK);
                    color.setStyle(Paint.Style.FILL);
                    canvas.drawRect(rect, color);
                    canvas.drawBitmap(bu, x, y, color);
                    sHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        public void pause(){
            resume = false;

        }

        public void resume(){
            resume = true;
            thread = new Thread(this);
            thread.start();
        }
    }


}