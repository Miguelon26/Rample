/*
* Miguel Andres Izaguirre Valerio
* Jorge Luis Vasquez Osorio
*
* Proyecto para Aplicaciones de Tecnologia: Rample
* MainActivity.java
*/

package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Animation button_Animation;
    ImageButton rample_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rample_Button = (ImageButton) findViewById(R.id.imageButton);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate);

        //Se obtiene la fecha y hora actuales
        Calendar rightNow = Calendar.getInstance();
        int currentTime = rightNow.get(Calendar.HOUR_OF_DAY);

        /*Dependiendo de la hora del día, sera mostrada una frase en la panatalla principal
         *  */
        TextView textViewHora = findViewById(R.id.textViewTime);
        if ((currentTime < 5)) {
            textViewHora.setText("Buenas noches");
        } else {
            if (currentTime < 12){
                textViewHora.setText("Buenos días");
            }else{
                if (currentTime < 19){
                    textViewHora.setText("Buenas tardes");
                }
                else {
                    textViewHora.setText("Buenas noches");
                }
            }

        }

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

        animateButton();

        //Transicion a activity de resultados
        Intent sharedIntent = new Intent(MainActivity.this, Results_1Activity.class);

        Pair pair= new Pair<ImageButton, String>(rample_Button,"buttonTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);

        startActivity(sharedIntent, options.toBundle());

    }//didTapButton

    //Animar el boton de Rample
    private void animateButton() {
        button_Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator();
        button_Animation.setInterpolator(interpolator);

        rample_Button.startAnimation(button_Animation);
    }//animateButton

}//MainActivity
