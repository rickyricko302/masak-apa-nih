package com.hikki.masakapanih.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.hikki.masakapanih.R;

import java.io.File;


public class SplashScreen extends AppCompatActivity {

    ImageView img;
    View v1,v2,v3,v4,v5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        img = findViewById(R.id.imageView);
        img.animate().alphaBy(1f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v1.setBackgroundColor(Color.parseColor("#FFFF8800"));
            }
        },500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v2.setBackgroundColor(Color.parseColor("#FFFF8800"));
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v3.setBackgroundColor(Color.parseColor("#FFFF8800"));
            }
        },1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v4.setBackgroundColor(Color.parseColor("#FFFF8800"));
            }
        },2500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v5.setBackgroundColor(Color.parseColor("#FFFF8800"));
                File f = new File(getFilesDir().getPath()+"/first");
                if(!f.exists()) {
                    f.mkdirs();
                    startActivity(new Intent(SplashScreen.this, AboutActivity.class));
                }
                else{
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            }
        },3000);
    }
}