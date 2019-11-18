/*
 * Miguel Andres Izaguirre Valerio
 * Jorge Luis Vasquez Osorio
 *
 * Proyecto para Aplicaciones de Tecnologia: Rample
 * MainActivity.java
 */

package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    TextView rample_textView;
    ImageView rample_imageView;
    ProgressBar progressBar;
    private static int SPLASH_SCREEN_TIME_OUT = 1500;//ms para splash screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        rample_textView = findViewById(R.id.rample_textView);
        rample_imageView = findViewById(R.id.rample_imageView);
        progressBar = findViewById(R.id.progressBar);

        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        //Handler e Intent para mostrar el splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
                finish();
            }
        },SPLASH_SCREEN_TIME_OUT);
    }//onCreate

}
