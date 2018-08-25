package com.example.dhruvupadhyaya.weatherapp;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;

public class ShowTemperatureActivity extends AppCompatActivity {
    private TextView celsiusTemp;
    private TextView tempTitle;
    private ImageView titleImage;
    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_temperature);

        celsiusTemp = findViewById(R.id.show_temp_a2);
        tempTitle = findViewById(R.id.temp_title);
        titleImage = findViewById(R.id.title_image);

        extra = getIntent().getExtras();
        //  https://api.openweathermap.org/data/2.5/weather?q=Delhi&appid=53ab2e7de867f3254fe592b7fb23d641


        final String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";

        final String API = "&appid=53ab2e7de867f3254fe592b7fb23d641";

        String myURL = baseURL + extra.get("CITYNAME") + API;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("JSON", "JSON: " + jsonObject);

                try {
                    String weather = jsonObject.getString("weather");
                    Log.i("WEATHER", "WEATHER : " + weather);

                    JSONArray ar = new JSONArray(weather);

                    for (int i = 0; i < ar.length(); i++) {

                        JSONObject par = ar.getJSONObject(i);

                        String myWeather = par.getString("main");
                        tempTitle.setText(myWeather);




                        if (myWeather.equals("Clouds")){
                            titleImage.setBackgroundResource(R.drawable.cloudy);
                        }else if (myWeather.equals("Rain")){
                            titleImage.setBackgroundResource(R.drawable.rain);
                        }else if (myWeather.equals("Haze")){
                            titleImage.setBackgroundResource(R.drawable.haze);
                        }else if (myWeather.equals("Clear")){
                            titleImage.setBackgroundResource(R.drawable.clear);
                        }else if (myWeather.equals("Snow")){
                            titleImage.setBackgroundResource(R.drawable.snow);
                        }else if (myWeather.equals("Sunny")){
                            titleImage.setBackgroundResource(R.drawable.sunny);
                        }else if (myWeather.equals("Mist")){
                            titleImage.setBackgroundResource(R.drawable.mist);
                        }

                        Log.i("ID", "ID " + par.getString("id"));
                        Log.i("ID", "MAIN " + par.getString("main"));

                    }


                    String main = jsonObject.getString("main");
                    Log.i("MAIN", "MAIN" + main);
                    JSONObject mainTemp = new JSONObject(main);
                    String temp = mainTemp.getString("temp");
                    Log.i("TEMPERATURE", "TEMPERATURE " + temp);

                    double celsius = Double.valueOf(temp) - 273.15 ;

                    BigDecimal bd = new BigDecimal(celsius);
                    bd = bd.round(new MathContext(3));
                    double rounded = bd.doubleValue();


                    celsiusTemp.setText(rounded + "°C");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", "VOLLY ERROR" + error);

                Toast.makeText(ShowTemperatureActivity.this, "Cannot get the weather..Please try again later or enter valid city name", Toast.LENGTH_LONG).show();
            }
        });


        MySingleton.getInstance(ShowTemperatureActivity.this).addToRequestQueue(jsonObjectRequest);






     }
    }


