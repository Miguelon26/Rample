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

public class ShowsResultsActivity extends AppCompatActivity {
    Animation button_Animation;
    ImageButton rample_imageButton;
    ProgressBar progressBar;
    ImageView poster_imageView;
    TextView title_textView;
    TextView info_textView;
    TextView homepage_textView;

    Bundle bundle;
    private Handler handler = new Handler();
    int apiDelay = 500;//ms

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
        String showURL = "https://api.themoviedb.org/3/tv/"+randomId+"?api_key=49e5c6a63c90a4e26771ad63fc77043c&language=en-US";

        bundle = getIntent().getExtras();//obtener filtros
        final String filtroGenero = bundle.getString("filtroGenero");
        final String filtroAnioShow = bundle.getString("filtroAnioShow");
        final String filtroEpisodio = bundle.getString("filtroEpisodio");
        final String filtroTemporada = bundle.getString("filtroTemporada");
        final String filtroRating = bundle.getString("filtroRating");
        Log.i("REST","Filtros: "+filtroGenero+" "+filtroAnioShow+" "+filtroEpisodio+" "+filtroTemporada+" "+filtroRating);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                showURL,//URL
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {//respuesta
                        Log.i("REST", response.toString());

                        try {
                            //Variables a extraer del API
                            String  firstAirDate="", homepage="", originalLanguage="", originalName= "", overview="", posterPath="", status="";
                            ArrayList<String> genres = new ArrayList<String>();
                            ArrayList<String> productionCompanies = new ArrayList<String>();
                            int episodeRunTime=0, year=0, id=0, numberOfEpisodes=0, numberOfSeasons=0;
                            double voteAverage=0.0;

                            //Proceso de extraer info del API
                            if (response.has("id")) {
                                id = response.getInt("id");
                                Log.i("REST",String.valueOf(id));
                            }

                            /*if (response.has("episode_run_time")) {
                                String episodeStringRunTime = response.getString("episode_run_time");
                                episodeStringRunTime.replaceAll("\\[]","");
                                episodeRunTime = Integer.parseInt(episodeStringRunTime);
                                Log.i("REST","Duracion episodio: "+episodeStringRunTime);
                            }*/


                            if (response.has("first_air_date")) {
                                firstAirDate = response.getString("first_air_date");

                                year = Integer.valueOf(firstAirDate.substring(0,4));
                                //Log.i("REST",String.valueOf(year));
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

                            if (response.has("number_of_episodes")) {
                                numberOfEpisodes = response.getInt("number_of_episodes");
                            }

                            if (response.has("number_of_seasons")) {
                                numberOfSeasons = response.getInt("number_of_seasons");
                                //Log.i("REST","Tempo: "+numberOfSeasons);
                            }

                            if (response.has("original_language")) {
                                originalLanguage = response.getString("original_language");
                            }

                            if (response.has("name")) {
                                originalName = response.getString("name");
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

                            if (response.has("status")) {
                                status = response.getString("status");
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
                                //Log.i("REST",tempGenero);

                                int tempAnioShowMin=1960, tempAnioShowMax=2020;
                                switch (filtroAnioShow) {
                                    case ">2010":
                                        tempAnioShowMin=2010;
                                        break;
                                    case "2000-2009":
                                        tempAnioShowMin=2000;
                                        tempAnioShowMax=2009;
                                        break;
                                    case "1990-1999":
                                        tempAnioShowMin=1990;
                                        tempAnioShowMax=1999;
                                        break;
                                    case "<1990":
                                        tempAnioShowMax=1989;
                                        break;
                                    default:
                                        break;
                                }//switch Año

                                /*int tempDuracionEpisodioMin=0, tempDuracionEpisodioMax=150;
                                switch (filtroEpisodio) {
                                    case ">60 minutos":
                                        tempDuracionEpisodioMin=60;
                                        break;
                                    case "30-60 minutos":
                                        tempDuracionEpisodioMin=30;
                                        tempDuracionEpisodioMax=60;
                                        break;
                                    case "<30 minutos":
                                        tempDuracionEpisodioMax=30;
                                        break;
                                    default:
                                        break;
                                }//switch duracion Episodio*/

                                int tempTemporadaMin=0, tempTemporadaMax=50;
                                switch (filtroTemporada) {
                                    case ">10":
                                        tempTemporadaMin=10;
                                        break;
                                    case "6-9":
                                        tempTemporadaMin=6;
                                        tempTemporadaMax=9;
                                        break;
                                    case "4-6":
                                        tempTemporadaMin=3;
                                        tempTemporadaMax=6;
                                        break;
                                    case "1-3":
                                        tempTemporadaMin=1;
                                        tempTemporadaMax=3;
                                        break;
                                    default:
                                        break;
                                }//switch Temporada

                                double tempRatingMin=0, tempRatingMax=10.0;
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


                                //3. Mostrar resultados
                                if (genres.contains(tempGenero) && ((year>=tempAnioShowMin)&&(year<=tempAnioShowMax))
                                        /*&& ((episodeRunTime>=tempDuracionEpisodioMin)&&(episodeRunTime<=tempDuracionEpisodioMax))*/ &&
                                        ((numberOfSeasons>=tempTemporadaMin)&&(numberOfSeasons<=tempTemporadaMax)) &&
                                        ((voteAverage>=tempRatingMin)&&(voteAverage<=tempRatingMax)) ) {
                                    progressBar.setVisibility(View.GONE);
                                    poster_imageView.setVisibility(View.VISIBLE);
                                    title_textView.setVisibility(View.VISIBLE);
                                    info_textView.setVisibility(View.VISIBLE);
                                    homepage_textView.setVisibility(View.VISIBLE);

                                    Picasso.get().load(posterPath).into(poster_imageView);
                                    title_textView.setText(Html.fromHtml("<b>" + originalName));
                                    info_textView.setText(Html.fromHtml("<b>Sinopsis: </b>" + overview + "\n<br><b>Año:</b> " + year + /*".\n<br><b>Duración por episodio:</b> " + episodeRunTime+ " minutos.\n" +*/
                                            "\n<br><b>Total de Episodios: </b>"+numberOfEpisodes+".\n<br><b>Total de Temporadas: </b>"+numberOfSeasons+".\n<br><b>Géneros:</b> " + genres + ".\n<br><b>Compañías:</b> "+ productionCompanies +
                                            ".\n<br><b>Rating:</b> " + voteAverage + "/100.\n<br><b>Status: </b>"+status+"."));
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




