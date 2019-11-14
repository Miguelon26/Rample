package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;

public class Results_1Activity extends AppCompatActivity {
    ImageButton rample_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_1);

        rample_Button = (ImageButton) findViewById(R.id.imageButton2);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }//onStart

    @Override
    protected void onResume() {
        super.onResume();
    }//onResume

    @Override
    protected void onPause() {
        super.onPause();
    }//onPause

    @Override
    protected void onStop() {
        super.onStop();
    }//onStop

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }//onDestroy

    @Override
    protected void onRestart() {
        super.onRestart();
    }//onRestart

    /**********************************************************************************************/
    public void didTapButton(View view) {
        // Do something in response to button click
        Log.d("RAMPLE", "Se ha presionado el boton.");

    }//didTapButton

}
