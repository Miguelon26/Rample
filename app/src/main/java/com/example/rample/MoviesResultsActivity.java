package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MoviesResultsActivity extends AppCompatActivity {
    Animation button_Animation;
    ImageButton rample_imageButton;
    ProgressBar progressBar;
    ImageView poster_imageView;
    TextView title_textView;
    TextView info_textView;
    TextView trailer_textView;

    String tempId = "";
    boolean containsGenre = false, containsYear = false, containsRuntime = false, containsCertification = false, containsRating = false, trailerClick = false;

    private Handler handler = new Handler();
    int apiDelay = 2000;//ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_results);

        rample_imageButton = findViewById(R.id.rample_imageButton);
        progressBar = findViewById(R.id.progressBar);
        poster_imageView = findViewById(R.id.poster_imageView);
        title_textView = findViewById(R.id.title_textView);
        info_textView = findViewById(R.id.info_textView);
        trailer_textView = findViewById(R.id.trailer_textView);

        //Mostrar y Ocultar
        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        poster_imageView.setVisibility(View.GONE);
        title_textView.setVisibility(View.GONE);
        info_textView.setVisibility(View.GONE);
        trailer_textView.setVisibility(View.GONE);

        //API loop
        //newRunnable.run();

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
        if (!trailerClick) {
            //API loop
            newRunnable.run();
        } else {//si se regresa a la app despues de ver el trailer
            trailerClick = false;
        }

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
        trailer_textView.setVisibility(View.GONE);

        //API loop
        handler.postDelayed(newRunnable, apiDelay);

    }//didTapButton

    private void runRequest() {
        String movieURL = "https://tv-v2.api-fetch.website/random/movie";

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
                            String id = "", title = "", year = "", synopsis = "", trailer = "", certification = "",
                                    genres = "", poster = "";
                            int runtime = 0, rating = 0;

                            id = response.getString("_id");
                            if (response.has("title")) {
                                title = response.getString("title");
                            }
                            if (response.has("year")) {
                                year = response.getString("year");
                            }
                            if (response.has("synopsis")) {
                                synopsis = response.getString("synopsis");
                            }
                            if (response.has("runtime")) {
                                runtime = response.getInt("runtime");
                            }
                            if (response.has("trailer")) {
                                trailer = response.getString("trailer");
                            }
                            if (response.has("certification")) {
                                certification = response.getString("certification");
                            }
                            if (response.has("genres")) {
                                genres = response.getString("genres");
                            }
                            if (response.has("images")) {
                                JSONObject images = response.getJSONObject("images");
                                if (images.has("poster")) {
                                    poster = images.getString("poster");
                                } else {
                                    poster = "NA";
                                }
                            }
                            if (response.has("rating")) {
                                JSONObject percentage = response.getJSONObject("rating");
                                if (percentage.has("percentage")) {
                                    rating = percentage.getInt("percentage");
                                } else {
                                    rating = 0;
                                }
                            }

                            //Obtener hasta 5 generos
                            genres = genres.replaceAll("[\"\\[\\]]", "");
                            String[] genre = genres.split(",");

                            /**********************************************************************/
                            //1. Comparar IDs
                            if (!tempId.equals(id)) {//si no se repite el resultado
                                tempId = id;

                                /*2. Revisar Filtros
                                for (int i = 0; i < 5; i++) {
                                    if (genre[i].contains("drama")) {
                                        containsGenre = true;
                                    }
                                }//for*/

                                //if (containsGenre) {
                                    //3. Mostrar resultados
                                    progressBar.setVisibility(View.GONE);
                                    poster_imageView.setVisibility(View.VISIBLE);
                                    title_textView.setVisibility(View.VISIBLE);
                                    info_textView.setVisibility(View.VISIBLE);
                                    trailer_textView.setVisibility(View.VISIBLE);

                                    poster = poster.substring(0, 4) + "s" + poster.substring(4);//subsrtr a https
                                    Picasso.get().load(poster).into(poster_imageView);
                                    title_textView.setText(Html.fromHtml("<br>" + title + "</b>"));
                                    info_textView.setText(Html.fromHtml("<b>Sinopsis: </b>" + synopsis + "\n<br><b>Año:</b> " + year + ".\n<br><b>Duración:</b> " + runtime + " minutos.\n" +
                                            "<br><b>Géneros:</b> " + genre[0] + " " + genre[1] + " " + genre[2] + ".\n<br><b>Clasificación:</b> " + certification + "\n.<br><b>Rating:</b> " + rating + "/100."));
                                    trailer_textView.setText(Html.fromHtml("<b>Trailer:</b> " + trailer));


                                    //requestQueue.stop();
                                    handler.removeCallbacks(newRunnable);
                                //}//if containsGenre
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


    public void onClick(View v) {
        trailerClick = true;
    }

    private void animateButton() {
        //Animar el boton de Rample
        button_Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        android.view.animation.BounceInterpolator interpolator = new BounceInterpolator();
        button_Animation.setInterpolator(interpolator);

        rample_imageButton.startAnimation(button_Animation);
    }//animateButton

}




