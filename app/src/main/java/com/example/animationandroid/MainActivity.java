package com.example.animationandroid;

import androidx.activity.result.contract.ActivityResultContracts;
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
import android.media.Image;
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

//Ali Mert Doganay
public class MainActivity extends Activity implements View.OnTouchListener {
    View view;
    Bitmap bu;
    Bitmap bearcat;
    float x,y = 0;
    int max = 800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new View(this);
        view.setOnTouchListener(this);
        bu = BitmapFactory.decodeResource(getResources(),R.drawable.bu1);
        bearcat = BitmapFactory.decodeResource(getResources(),R.drawable.bearcat);

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
        SurfaceHolder sHolder1 = null;

        int action = event.getAction();


        switch (action) {
            case MotionEvent.ACTION_DOWN:

                onPause();
                Canvas canvas = view.sHolder.lockCanvas();
                Rect rect = new Rect();
                rect.set(0, 0, canvas.getWidth(), canvas.getHeight());
                Paint color = new Paint();
                color.setColor(Color.WHITE);
                color.setStyle(Paint.Style.FILL);
                canvas.drawRect(rect, color);
                canvas.drawBitmap(bearcat, x, y, color);

                view.sHolder.unlockCanvasAndPost(canvas);
                

                break;

            case MotionEvent.ACTION_UP:

                onResume();
                break;

         }
        return true;
    }



    public class View extends SurfaceView implements Runnable{

        boolean resume = false;
        Thread thread = null;
        SurfaceHolder sHolder;


        public View(Context context){
            super(context);
            sHolder = getHolder();

        }


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