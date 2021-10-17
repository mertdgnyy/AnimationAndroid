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
import android.util.Base64;
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
    int count = 0;

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
        super.onPause();               //Stopping bitmap to move
        view.pause();
    }

    @Override
    protected void onResume() {         //Starting bitmap move again
        super.onResume();
        view.resume();
    }

    public static Bitmap scaleUp(Bitmap img, float maxSize,boolean fltr){
        float ratio = Math.min(
                (float) maxSize / img.getWidth(),
                (float) maxSize / img.getHeight());
        int width = Math.round((float) ratio * img.getWidth());
        int height = Math.round((float) ratio * img.getHeight());

        Bitmap newBit = Bitmap.createScaledBitmap(img,width,height,fltr);
        return newBit;
    }



    //This is the part that if user clicks the screen bitmap freezes, and if release clicking,
    // canvas color changes to white, bitmap image changes to bearcat, and everytime user clicks it
    //bitmapsize increases with the 200-count size, count is decreasing 10 by each click.

    @Override
    public boolean onTouch(android.view.View v, MotionEvent event) {

        SurfaceHolder sHolder1 = null;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                onPause();
                count -=10;
                Bitmap scaledbearcat = scaleUp(bearcat,200-count,true);
                Canvas canvas = view.sHolder.lockCanvas();
                Rect rect = new Rect();
                rect.set(0, 0, canvas.getWidth(), canvas.getHeight());
                Paint color = new Paint();
                color.setColor(Color.WHITE);
                color.setStyle(Paint.Style.FILL);
                canvas.drawRect(rect, color);
                canvas.drawBitmap(scaledbearcat, x, y, color);
                view.sHolder.unlockCanvasAndPost(canvas);
                break;

            case MotionEvent.ACTION_UP:

                onResume();
                break;
         }
        return true;
    }



    public class View extends SurfaceView implements Runnable{

        //creating variables

        boolean resume = false;
        Thread thread = null;
        SurfaceHolder sHolder;


        public View(Context context){
            super(context);
            sHolder = getHolder();

        }


        //Drawing a black canvas with surfaceview, random movement of x, and y axis implemented here, also BU bitmap size increases here in Bitmap Scaledbu part.
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
                    Bitmap scaledbu = scaleUp(bu,200-count,true);
                    canvas.drawBitmap(scaledbu, x, y, color);
                    sHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        public void pause(){
            resume = false;
        }    //I access these with onPause and onResume

        public void resume(){
            resume = true;
            thread = new Thread(this);
            thread.start();
        }


    }


}