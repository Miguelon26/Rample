/*
* Miguel Andres Izaguirre Valerio
* Jorge Luis Vasquez Osorio
*
* Proyecto para Aplicaciones de Tecnologia: Rample
* MainActivity.java
*/

package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Animation button_Animation;
    ImageButton rample_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rample_Button = (ImageButton) findViewById(R.id.imageButton);

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
        animateButton();
    }//onStart

    @Override
    protected void onResume() {
        super.onResume();
        animateButton();
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

    //Al presionar el boton de Rample
    public void didTapButton(View view) {
        // Do something in response to button click
        Log.d("RAMPLE", "Se ha presionado el boton.");

    }//didTapButton

    //Animar el boton de Rample
    private void animateButton() {
        button_Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator();
        button_Animation.setInterpolator(interpolator);

        rample_Button.startAnimation(button_Animation);
    }//animateButton

}//MainActivity
