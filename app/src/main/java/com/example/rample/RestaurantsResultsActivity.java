/*
 * Jorge Luis Vasquez Osorio
 *
 * Proyecto para Aplicaciones de Tecnologia: Rample
 * MainActivity.java
 */

package com.example.rample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import devaxt.devazt.networking.HttpClient;
import devaxt.devazt.networking.OnHttpRequestComplete;
import devaxt.devazt.networking.Response;

public class RestaurantsResultsActivity extends AppCompatActivity {

    ImageButton rample_imageButton;
    TextView restaurantName;
    TextView restaurantPrice;
    TextView restaurantRating;
    TextView restaurantVicinity;
    ImageView restaurantPhoto;

    String tipoComida;
    String distancia;
    String ratingFiltro;
    String precioFiltro;

    Bundle bundle;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_results);

        rample_imageButton = findViewById(R.id.rample_imageButton);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantRating = findViewById(R.id.restaurantRating);
        restaurantPrice = findViewById(R.id.restaurantPrice);
        restaurantVicinity = findViewById(R.id.restaurantVicinity);
        restaurantPhoto = findViewById(R.id.restaurantPhoto);



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        runRequest();

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
        runRequest();

    }//didTapButton

    private void runRequest() {
        bundle = getIntent().getExtras();
        final String filtroTipoComida = bundle.getString("filtroTipoComida");
        final String filtroDistancia = bundle.getString("filtroDistancia");
        final String filtroFoodRating = bundle.getString("filtroFoodRating");
        final String filtroPrecio = bundle.getString("filtroPrecio");
        Log.i("REST", "Filtros: " + filtroTipoComida + " " + filtroDistancia + " " + filtroFoodRating + " " + filtroPrecio + " ");


        switch (filtroTipoComida) {
            case "Tipo":
                tipoComida = "";
                break;
            case "China":
                tipoComida = "&keyword=chinese";
                break;
            case "Italiana":
                tipoComida = "&keyword=italian";
                break;
            case "Hamburguesas":
                tipoComida = "&keyword=hamburguer";
                break;
            case "Pizza":
                tipoComida = "&keyword=mexican";
                break;
            default:
                break;
        }

        //Filtrar por distancia
        switch (filtroDistancia) {
            case "Distancia":
                distancia = "&radius=50000";
                break;
            case "1 km":
                distancia = "&radius=1000";
                break;
            case "3 km":
                distancia = "&radius=3000";
                break;
            case "5 km":
                distancia = "&radius=5000";
                break;
            case "10 km":
                distancia = "&radius=10000";
                break;
            case "20 km":
                distancia = "&radius=20000";
                break;
            case "50 km":
                distancia = "50000";
                break;
            default:
                break;
        }


        LocationManager locationManager = (LocationManager) RestaurantsResultsActivity.this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //textViewLocation.setText("" + location.getLatitude() + " " + location.getLongitude());
                String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location.getLatitude() + "," + location.getLongitude() + distancia + tipoComida + "&type=restaurant&key=AIzaSyAGeCDbjwRQ4x0YThvKvttxBeZU3Tt6kCQ";
                Log.i("REST", URL);
                request(URL);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        int permissionCheck = ContextCompat.checkSelfPermission(RestaurantsResultsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    private void request(String URL) {
        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    Gson gson = new GsonBuilder().create();
                    try {
                        JSONObject jsono = new JSONObject(status.getResult());
                        JSONArray jsonarray = jsono.getJSONArray("results");
                        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
                        Random r = new Random();
                        int random = r.nextInt(jsonarray.length() + 1);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            String restaurant = jsonarray.getString(random);
                            Log.i("REST", restaurant);

                            Restaurant name = gson.fromJson(restaurant, Restaurant.class);
                            restaurants.add(name);
                            restaurantName.setText(name.getName());


                            Restaurant price = gson.fromJson(restaurant, Restaurant.class);
                            restaurants.add(price);
                            if (price.getPrice_level() == null) {
                                restaurantPrice.setText("Precio: No hay");
                            } else {
                                restaurantPrice.setText("Nivel de precio: " + price.getPrice_level());
                            }

                            Restaurant rating = gson.fromJson(restaurant, Restaurant.class);
                            restaurants.add(rating);
                            if (rating.getRating() == null) {
                                restaurantRating.setText("Rating: No hay");
                            } else {
                                restaurantRating.setText("Rating: " + rating.getRating());
                            }

                            Restaurant vicinity = gson.fromJson(restaurant, Restaurant.class);
                            restaurants.add(vicinity);
                            if (vicinity.getVecinity() == null) {
                                restaurantVicinity.setText("Dirección: No hay");
                            } else {
                                restaurantVicinity.setText("Dirección: " + vicinity.getVecinity());
                            }

                            Restaurant photo = gson.fromJson(restaurant, Restaurant.class);
                            restaurants.add(photo);
                            Log.i("REST", photo.getPhoto_reference());
                            Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyAGeCDbjwRQ4x0YThvKvttxBeZU3Tt6kCQ&photoreference=" + photo.getPhoto_reference()).into(restaurantPhoto);

                        }
                        Restaurant restaurant = gson.fromJson(status.getResult(), Restaurant.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(RestaurantsResultsActivity.this, status.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        client.excecute(URL);


    }
}
