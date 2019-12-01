/*
 * Miguel Andres Izaguirre Valerio
 *
 * Proyecto para Aplicaciones de Tecnologia: Rample
 * MoviesResultsActivity.java
 */

package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MoviesResultsActivity extends AppCompatActivity {
    Animation button_Animation;
    ImageButton rample_imageButton;
    ProgressBar progressBar;
    ImageView poster_imageView;
    TextView title_textView;
    TextView info_textView;
    TextView homepage_textView;

    Bundle bundle;
    private Handler handler = new Handler();
    int apiDelay = 1000;//ms

    int tempId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_results);

        rample_imageButton = findViewById(R.id.rample_imageButton);
        progressBar = findViewById(R.id.progressBar);
        poster_imageView = findViewById(R.id.poster_imageView);
        title_textView = findViewById(R.id.title_textView);
        info_textView = findViewById(R.id.info_textView);
        info_textView.setMovementMethod(new ScrollingMovementMethod());
        homepage_textView = findViewById(R.id.homepage_textView);

        //Mostrar y Ocultar
        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        poster_imageView.setVisibility(View.GONE);
        title_textView.setVisibility(View.GONE);
        info_textView.setVisibility(View.GONE);
        homepage_textView.setVisibility(View.GONE);

        //API loop
        newRunnable.run();

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
            //API loop
            //newRunnable.run();
    }//onStart

    @Override
    protected void onResume() {
        super.onResume();

    }//onResume

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(newRunnable);
    }//onPause

    @Override
    protected void onStop() {
        super.onStop();
    }//onStop

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(newRunnable);
    }//onDestroy

    @Override
    protected void onRestart() {
        super.onRestart();
    }//onRestart

    /**********************************************************************************************/
    public void didTapButton(View view) {
        // Do something in response to button click
        Log.d("RAMPLE", "Se ha presionado el boton.");
        animateButton();

        //Mostrar y Ocultar
        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        poster_imageView.setVisibility(View.GONE);
        title_textView.setVisibility(View.GONE);
        info_textView.setVisibility(View.GONE);
        homepage_textView.setVisibility(View.GONE);

        //API loop
        handler.postDelayed(newRunnable, apiDelay);

    }//didTapButton

    private void runRequest() {
        final int random = new Random().nextInt(600000);//id aleatorio
        String randomId = String.valueOf(random);
        //Log.i("REST","Random: "+randomId);
        String movieURL = "https://api.themoviedb.org/3/movie/"+randomId+"?api_key=49e5c6a63c90a4e26771ad63fc77043c&language=en-US";

        bundle = getIntent().getExtras();//obtener filtros
        final String filtroGenero = bundle.getString("filtroGenero");
        final String filtroAnio = bundle.getString("filtroAnio");
        final String filtroDuracion = bundle.getString("filtroDuracion");
        final String filtroRating = bundle.getString("filtroRating");
        final String filtroParaAdultos = bundle.getString("filtroParaAdultos");
        //Log.i("REST","Filtros: "+filtroGenero+" "+filtroAnio+" "+filtroDuracion+" "+filtroRating+" "+filtroParaAdultos);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                movieURL,//URL
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {//respuesta
                        Log.i("REST", response.toString());

                        try {
                            //Variables a extraer del API
                            String homepage="", imdbId="", originalLanguage="", originalTitle= "", overview="", posterPath="", releaseDate="", status="", tagline="";
                            ArrayList<String> genres = new ArrayList<String>();
                            ArrayList<String> productionCompanies = new ArrayList<String>();
                            ArrayList<String> spokenLanguages = new ArrayList<String>();
                            int year=0, runtime = 0, id=0;
                            double voteAverage=0.0;
                            boolean adult=false;

                            //Proceso de extraer info del API
                            if (response.has("id")) {
                                id = response.getInt("id");
                                //Log.i("REST",String.valueOf(id));
                            }

                            if (response.has("adult")) {
                                adult = response.getBoolean("adult");
                            }

                            if (response.has("genres")) {
                                JSONArray genresJSONArray = response.getJSONArray("genres");
                                for (int i=0; i<genresJSONArray.length(); i++) {
                                    JSONObject genresJSONObject = genresJSONArray.getJSONObject(i);
                                    String genre=genresJSONObject.getString("name");
                                    //Log.i("REST","Genero: "+genre);
                                    genres.add(genre);
                                }
                                //Log.i("REST","Géneros: "+genres);
                            }

                            if (response.has("homepage")) {
                                homepage = response.getString("homepage");
                            }

                            if (response.has("imdb_id")) {
                                imdbId = response.getString("imdb_id");
                            }

                            if (response.has("original_language")) {
                                originalLanguage = response.getString("original_language");
                            }

                            if (response.has("original_title")) {
                                originalTitle = response.getString("original_title");
                            }

                            if (response.has("overview")) {
                                overview = response.getString("overview");
                            }

                            if (response.has("poster_path")) {
                                posterPath = response.getString("poster_path");
                                posterPath="https://image.tmdb.org/t/p/w500"+posterPath;
                            }

                            if (response.has("production_companies")) {
                                JSONArray productionCompaniesJSONArray = response.getJSONArray("production_companies");
                                for (int i=0; i<productionCompaniesJSONArray.length(); i++) {
                                    JSONObject productionCompaniesJSONObject = productionCompaniesJSONArray.getJSONObject(i);
                                    String productionCompany=productionCompaniesJSONObject.getString("name");
                                    productionCompanies.add(productionCompany);
                                }
                            }

                            if (response.has("release_date")) {
                                releaseDate = response.getString("release_date");

                                year = Integer.valueOf(releaseDate.substring(0,4));
                            }

                            if (response.has("runtime")) {
                                runtime = response.getInt("runtime");
                            }

                            if (response.has("spoken_languages")) {
                                JSONArray spokenLanguagesJSONArray = response.getJSONArray("spoken_languages");
                                for (int i=0; i<spokenLanguagesJSONArray.length(); i++) {
                                    JSONObject spokenLanguagesJSONObject = spokenLanguagesJSONArray.getJSONObject(i);
                                    String spokenLanguage=spokenLanguagesJSONObject.getString("name");
                                    spokenLanguages.add(spokenLanguage);
                                }
                            }


                            if (response.has("status")) {
                                status = response.getString("status");
                            }

                            if (response.has("tagline")) {
                                tagline = response.getString("tagline");
                            }

                            if (response.has("vote_average")) {
                                voteAverage = response.getDouble("vote_average");
                            }

                            /**********************************************************************/
                            //1. Comparar IDs
                            if (tempId!=id) {//si no se repite el resultado
                                tempId = id;

                                //2. Revisar Filtros
                                String tempGenero="";
                                if (filtroGenero.equals("Género")) {//cualquier genero
                                    tempGenero=genres.get(0);
                                } else { tempGenero=filtroGenero;}//genero correcto

                                int tempAnioMin=1950, tempAnioMax=2020;
                                switch (filtroAnio) {
                                    case ">2010":
                                        tempAnioMin=2010;
                                        break;
                                    case "2000-2009":
                                        tempAnioMin=2000;
                                        tempAnioMax=2009;
                                        break;
                                    case "1990-1999":
                                        tempAnioMin=1990;
                                        tempAnioMax=1999;
                                        break;
                                    case "1980-1989":
                                        tempAnioMin=1980;
                                        tempAnioMax=1989;
                                        break;
                                    case "<1980":
                                        tempAnioMax=1979;
                                        break;
                                    default:
                                        break;
                                }//switch Año

                                int tempDuracionMin=15, tempDuracionMax=300;
                                switch (filtroDuracion) {
                                    case ">120 minutos":
                                        tempDuracionMin=120;
                                        break;
                                    case "90-120 minutos":
                                        tempDuracionMin=90;
                                        tempDuracionMax=120;
                                        break;
                                    case "<90 minutos":
                                        tempDuracionMax=89;
                                        break;
                                    default:
                                        break;
                                }//switch Duracion

                                double tempRatingMin=0.0, tempRatingMax=10.0;
                                switch (filtroRating) {
                                    case ">9.0":
                                        tempRatingMin=9.0;
                                        break;
                                    case "8.0-8.9":
                                        tempRatingMin=8.0;
                                        tempRatingMax=8.9;
                                        break;
                                    case "7.0-7.9":
                                        tempRatingMin=7.0;
                                        tempRatingMax=7.9;
                                        break;
                                    case "6.0-6.9":
                                        tempRatingMin=6.0;
                                        tempRatingMax=6.9;
                                        break;
                                    case "<6.0":
                                        tempRatingMax=5.9;
                                        break;
                                    default:
                                        break;
                                }//switch Duracion

                                boolean tempParaAdultos=false;
                                if (filtroParaAdultos.equals("Sí")) {//pelis adultos
                                    tempParaAdultos=true;
                                }




                                //3. Mostrar resultados
                                if (genres.contains(tempGenero) && ((year>=tempAnioMin)&&(year<=tempAnioMax))
                                && ((runtime>=tempDuracionMin)&&(runtime<=tempDuracionMax)) &&
                                        ((voteAverage>=tempRatingMin)&&(voteAverage<=tempRatingMax)) &&
                                adult==tempParaAdultos) {
                                    progressBar.setVisibility(View.GONE);
                                    poster_imageView.setVisibility(View.VISIBLE);
                                    title_textView.setVisibility(View.VISIBLE);
                                    info_textView.setVisibility(View.VISIBLE);
                                    homepage_textView.setVisibility(View.VISIBLE);

                                    Picasso.get().load(posterPath).into(poster_imageView);
                                    title_textView.setText(Html.fromHtml("<b>" + originalTitle + "</b><br><i><center>"+tagline+"</center></i>"));
                                    info_textView.setText(Html.fromHtml("<br><b>Sinopsis: </b>" + overview + "\n<br><b>Año:</b> " + year + ".\n<br><b>Duración:</b> " + runtime + " minutos.\n" +
                                            "<br><b>Géneros:</b> " + genres + ".\n<br><b>Rating:</b> " + voteAverage + "/100.\n<br><b>Compañías:</b> "+ productionCompanies +
                                            ".\n<br><b>Status: </b>"+status+"."));
                                    homepage_textView.setText(Html.fromHtml("<b>Sitio web:</b> " + homepage));


                                    //requestQueue.stop();
                                    handler.removeCallbacks(newRunnable);
                                }//if genres
                            } else {//si es el mismo id
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST", error.toString());
                    }
                }
        );//requestQueue

        requestQueue.add(objectRequest);
        requestQueue.getCache().clear();


    }//runRequest

    private Runnable newRunnable = new Runnable() {
        @Override
        public void run() {
            runRequest();
            handler.postDelayed(newRunnable, apiDelay);
        }
    };

    private void animateButton() {
        //Animar el boton de Rample
        button_Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        android.view.animation.BounceInterpolator interpolator = new BounceInterpolator();
        button_Animation.setInterpolator(interpolator);

        rample_imageButton.startAnimation(button_Animation);
    }//animateButton

}




