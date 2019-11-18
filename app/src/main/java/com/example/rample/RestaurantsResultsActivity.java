package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class RestaurantsResultsActivity extends AppCompatActivity {
    ImageButton rample_imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_results);

        rample_imageButton = findViewById(R.id.rample_imageButton);

    }//onCreate

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
        Log.d("REST", "Se ha presionado el boton.");


    }//didTapButton
}
