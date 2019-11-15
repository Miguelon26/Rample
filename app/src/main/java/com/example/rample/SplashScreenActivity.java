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
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 1000;//ms para splash screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
