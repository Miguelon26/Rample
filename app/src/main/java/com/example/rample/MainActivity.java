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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Animation button_Animation;
    TextView bienvenido_textView;
    TextView fecha_textView;
    ImageButton rample_imageButton;
    Spinner categoria_spinner;
    Spinner filtro1_spinner;
    Spinner filtro2_spinner;
    Spinner filtro3_spinner;
    Spinner filtro4_spinner;
    Spinner filtro5_spinner;
    Spinner filtro6_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bienvenido_textView = (TextView) findViewById(R.id.bienvenido_textView);
        fecha_textView = (TextView) findViewById(R.id.fecha_textView);
        rample_imageButton = (ImageButton) findViewById(R.id.rample_imageButton);
        categoria_spinner = (Spinner) findViewById(R.id.categoria_spinner);
        filtro1_spinner = (Spinner) findViewById(R.id.filtro1_spinner);
        filtro2_spinner = (Spinner) findViewById(R.id.filtro2_spinner);
        filtro3_spinner = (Spinner) findViewById(R.id.filtro3_spinner);
        filtro4_spinner = (Spinner) findViewById(R.id.filtro4_spinner);
        filtro5_spinner = (Spinner) findViewById(R.id.filtro5_spinner);
        filtro6_spinner = (Spinner) findViewById(R.id.filtro6_spinner);

        filtro1_spinner.setVisibility(View.GONE);
        filtro2_spinner.setVisibility(View.GONE);
        filtro3_spinner.setVisibility(View.GONE);
        filtro4_spinner.setVisibility(View.GONE);
        filtro5_spinner.setVisibility(View.GONE);
        filtro6_spinner.setVisibility(View.GONE);

        String[] categorias = new String[]{"Películas", "Series", "Restaurantes"};
        //Filtros Peliculas
        final String[] filtroGeneros = new String[]{"Género", "Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"};
        final String[] filtroAdultos = new String[]{"Para Adultos", "Sí", "No"};
        final String[] filtroAnio = new String[]{"Año", ">2010", "2000-2009", "1990-1999", "1980-1989", "<1980"};
        final String[] filtroRuntime = new String[]{"Duración", ">120 minutos", "90-120 minutos", "<90 minutos"};
        final String[] filtroRating = new String[]{"Rating", ">9.0", "8.0-8.9", "7.0-7.9", "6.0-6.9", "<6.0"};
        //Filtros Series
        final String[] filtroAnioShows = new String[]{"Año",">2010","2000-2009","1990-1999","<1990"};
        final String[] filtroEpisodios = new String[]{"Episodios","<30 minutos","30-60 minutos",">60 minutos"};
        final String[] filtroTemporadas = new String[]{"Temporadas","1-3","4-6","6-9",">10"};

        //Agregar Dropdown lists
        ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, categorias);
        categoria_spinner.setAdapter(categoriasAdapter);
        categoria_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Determinar filtros a mostrar por categoria
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaElegida = categoria_spinner.getSelectedItem().toString();
                switch (categoriaElegida) {
                    case "Películas":
                        ArrayAdapter<String> filtro1Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroGeneros);
                        filtro1_spinner.setAdapter(filtro1Adapter);
                        filtro1_spinner.setVisibility(View.VISIBLE);

                        ArrayAdapter<String> filtro2Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroAnio);
                        filtro2_spinner.setAdapter(filtro2Adapter);
                        filtro2_spinner.setVisibility(View.VISIBLE);

                        ArrayAdapter<String> filtro3Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroRuntime);
                        filtro3_spinner.setAdapter(filtro3Adapter);
                        filtro3_spinner.setVisibility(View.VISIBLE);

                        ArrayAdapter<String> filtro4Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroRating);
                        filtro4_spinner.setAdapter(filtro4Adapter);
                        filtro4_spinner.setVisibility(View.VISIBLE);

                        ArrayAdapter<String> filtro5Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroAdultos);
                        filtro5_spinner.setAdapter(filtro5Adapter);
                        filtro5_spinner.setVisibility(View.VISIBLE);

                        filtro6_spinner.setVisibility(View.GONE);
                        break;
                    case "Series":
                        filtro1Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroGeneros);
                        filtro1_spinner.setAdapter(filtro1Adapter);
                        filtro1_spinner.setVisibility(View.VISIBLE);

                        filtro2Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroAnioShows);
                        filtro2_spinner.setAdapter(filtro2Adapter);
                        filtro2_spinner.setVisibility(View.VISIBLE);

                        filtro3Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroEpisodios);
                        filtro3_spinner.setAdapter(filtro3Adapter);
                        filtro3_spinner.setVisibility(View.VISIBLE);

                        filtro4Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroTemporadas);
                        filtro4_spinner.setAdapter(filtro4Adapter);
                        filtro4_spinner.setVisibility(View.VISIBLE);

                        filtro5Adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, filtroRating);
                        filtro5_spinner.setAdapter(filtro5Adapter);
                        filtro5_spinner.setVisibility(View.VISIBLE);

                        filtro6_spinner.setVisibility(View.GONE);
                        break;
                    case "Restaurantes":
                        filtro1_spinner.setVisibility(View.GONE);
                        filtro2_spinner.setVisibility(View.GONE);
                        filtro3_spinner.setVisibility(View.GONE);
                        filtro4_spinner.setVisibility(View.GONE);
                        filtro5_spinner.setVisibility(View.GONE);
                        filtro6_spinner.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }//switch
                /*if (categoria_spinner.getSelectedItem().toString().equals("Películas")) {
                    ArrayAdapter<String> filtro1Adapter = new ArrayAdapter<>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,filtroGeneros);
                    filtro1_spinner.setAdapter(filtro1Adapter);
                    filtro1_spinner.setVisibility(View.VISIBLE);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });


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
            if (currentTime < 12) {
                bienvenido_textView.setText("Buenos días");
            } else {
                if (currentTime < 19) {
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
        Log.i("REST", "Categoria Elegida: " + categoriaElegida);

        switch (categoriaElegida) {
            case "Películas":
                String filtro1 = filtro1_spinner.getSelectedItem().toString();
                String filtro2 = filtro2_spinner.getSelectedItem().toString();
                String filtro3 = filtro3_spinner.getSelectedItem().toString();
                String filtro4 = filtro4_spinner.getSelectedItem().toString();
                String filtro5= filtro5_spinner.getSelectedItem().toString();
                String filtro6="";

                sharedIntent = new Intent(MainActivity.this, MoviesResultsActivity.class);
                sharedIntent.putExtra("filtroGenero",filtro1);
                sharedIntent.putExtra("filtroAnio",filtro2);
                sharedIntent.putExtra("filtroDuracion",filtro3);
                sharedIntent.putExtra("filtroRating",filtro4);
                sharedIntent.putExtra("filtroParaAdultos",filtro5);
                break;
            case "Series":
                filtro1 = filtro1_spinner.getSelectedItem().toString();
                filtro2 = filtro2_spinner.getSelectedItem().toString();
                filtro3 = filtro3_spinner.getSelectedItem().toString();
                filtro4 = filtro4_spinner.getSelectedItem().toString();
                filtro5= filtro5_spinner.getSelectedItem().toString();
                filtro6="";

                sharedIntent = new Intent(MainActivity.this, ShowsResultsActivity.class);
                sharedIntent.putExtra("filtroGenero",filtro1);
                sharedIntent.putExtra("filtroAnioShow",filtro2);
                sharedIntent.putExtra("filtroEpisodio",filtro3);
                sharedIntent.putExtra("filtroTemporada",filtro4);
                sharedIntent.putExtra("filtroRating",filtro5);
                break;
            case "Restaurantes":
                sharedIntent = new Intent(MainActivity.this, RestaurantsResultsActivity.class);
                break;
            default:

                break;
        }//switch

        Pair pair = new Pair<ImageButton, String>(rample_imageButton, "buttonTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair);
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
