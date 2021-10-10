package com.example.animationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Draw d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d = new Draw(MainActivity.this);
        setContentView(d);



    }
}