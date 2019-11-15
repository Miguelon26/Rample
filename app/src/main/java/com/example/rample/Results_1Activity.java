package com.example.rample;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Results_1Activity extends AppCompatActivity {
    ImageButton rample_Button;
    TextView enlaceRandom_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_1);

        rample_Button = findViewById(R.id.imageButton2);
        enlaceRandom_TextView = findViewById(R.id.resultados);

        enlaceRandom_TextView.setText("Cargando tu experiencia...");
        generateRandom();

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
        Log.d("RAMPLE", "Se ha presionado el boton.");

    }//didTapButton


    private void generateRandom() {
        String movieURL = "https://tv-v2.api-fetch.website/random/movie";
        String showURL = "https://tv-v2.api-fetch.website/random/show";

        //Peticion para pelicula aleatoria
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                movieURL,//que URL esta pidiendo
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {//respuesta
                        Log.i("REST",response.toString());

                        try {
                            String title="", year ="",synopsis="",runtime="",trailer="",certification="",
                            genres="",poster="";
                            int rating=0;

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
                                runtime = response.getString("runtime");
                            }
                            if (response.has("trailer")) {
                                trailer = response.getString("trailer");
                            } else { trailer = "NA"; }
                            if (response.has("certification")) {
                                certification = response.getString("certification");
                            } else { certification = "NA"; }
                            if (response.has("genres")) {
                                genres = response.getString("genres");
                            }
                            if (response.has("images")) {
                                JSONObject images = response.getJSONObject("images");
                                if (images.has("poster")) {
                                    poster = images.getString("poster");
                                } else {poster="NA";}
                            }
                            if (response.has("rating")) {
                                JSONObject percentage = response.getJSONObject("rating");
                                if (percentage.has("percentage")) {
                                    rating = percentage.getInt("percentage");
                                } else { rating = 0; }
                            }

                            enlaceRandom_TextView.setText("Título: "+title+"\nAño: "+year+"\nSinopsis: "+synopsis+
                                    "\nDuración: "+runtime+" minutos\nTrailer: "+trailer+"\nClasificación: "+
                                    certification+"\nGéneros: "+genres+"\nPoster: "+poster+"\nRating: "+rating);
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST",error.toString());
                    }
                }
        );//requestQueue

        requestQueue.add(objectRequest);
    }//generateRandom


}

