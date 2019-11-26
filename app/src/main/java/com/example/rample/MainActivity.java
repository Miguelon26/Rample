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
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Animation button_Animation;
    TextView bienvenido_textView;
    TextView fecha_textView;
    ImageButton rample_imageButton;
    Spinner categoria_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bienvenido_textView = (TextView) findViewById(R.id.bienvenido_textView);
        fecha_textView = (TextView) findViewById(R.id.fecha_textView);
        rample_imageButton = (ImageButton) findViewById(R.id.rample_imageButton);
        categoria_spinner = (Spinner) findViewById(R.id.categoria_spinner);

        String[] categorias = new String[]{"Películas", "Series", "Anime","Restaurantes"};
        //String[] filtroGeneros;

        //Agregar Dropdown lists
        ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,categorias);
        categoria_spinner.setAdapter(categoriasAdapter);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        fecha_textView.setText(currentDate);

        //Se obtiene la fecha y hora actuales
        Calendar rightNow = Calendar.getInstance();
        int currentTime = rightNow.get(Calendar.HOUR_OF_DAY);

        //Dependiendo de la hora del día, mostrar frase de bienvenida
        if ((currentTime < 5)) {
            bienvenido_textView.setText("Buenas noches");
        } else {
            if (currentTime < 12){
                bienvenido_textView.setText("Buenos días");
            } else {
                if (currentTime < 19){
                    bienvenido_textView.setText("Buenas tardes");
                } else {
                    bienvenido_textView.setText("Buenas noches");
                }
            }
        }

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
        //animateButton();
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
        Intent sharedIntent = new Intent();//Transicion a activity de resultados

        // Do something in response to button click
        Log.d("REST", "Se ha presionado el boton.");

        animateButton();

        //Validar a que activity ir
        String categoriaElegida = categoria_spinner.getSelectedItem().toString();
        Log.i("REST", "Categoria Elegida: "+categoriaElegida);

        switch (categoriaElegida) {
            case "Películas":
                sharedIntent = new Intent(MainActivity.this, MoviesResultsActivity.class);
                break;
            case "Restaurantes":
                sharedIntent = new Intent(MainActivity.this, RestaurantsResultsActivity.class);
                break;
            default:

                break;
        }//switch

        Pair pair= new Pair<ImageButton, String>(rample_imageButton,"buttonTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);
        startActivity(sharedIntent, options.toBundle());

    }//didTapButton

    private void animateButton() {
        //Animar el boton de Rample
        button_Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator();
        button_Animation.setInterpolator(interpolator);

        rample_imageButton.startAnimation(button_Animation);
    }//animateButton

}//MainActivity
