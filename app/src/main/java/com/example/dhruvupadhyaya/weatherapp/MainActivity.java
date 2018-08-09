package com.example.dhruvupadhyaya.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText city;
    private TextView result;
    private TextView temprature;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.cityId);
        result = findViewById(R.id.displayWeather);
        temprature = findViewById(R.id.tempratureId);
        imageView = findViewById(R.id.imageView);

      //  https://api.openweathermap.org/data/2.5/weather?q=Delhi&appid=53ab2e7de867f3254fe592b7fb23d641
        final String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";

        final String API = "&appid=53ab2e7de867f3254fe592b7fb23d641";

        if (city.getText().toString() == null){
            Toast.makeText(this, "Please enter city name", Toast.LENGTH_SHORT).show();
        }else {

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String myURL = baseURL + city.getText().toString() +API;
                    JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.i("JSON","JSON: " + jsonObject);

                            try {
                                String weather = jsonObject.getString("weather");
                                Log.i("WEATHER","WEATHER : " + weather);

                                JSONArray ar = new JSONArray(weather);

                                for (int i = 0;i<ar.length();i++){

                                    JSONObject par = ar.getJSONObject(i);

                                    String myWeather = par.getString("main");
                                    result.setText(myWeather);
                                  //  imageView.setBackgroundResource(R.drawable.clouds);


                                    if (myWeather.equals("Clouds")){
                                        imageView.setBackgroundResource(R.drawable.clouds);
                                        Log.i("MYWEATHER" ,"WEATHER" + myWeather);
                                    }else if (myWeather.equals("Haze")){
                                        imageView.setBackgroundResource(R.drawable.haze);
                                    }else if (myWeather.equals("Rain")){
                                        imageView.setBackgroundResource(R.drawable.rain);
                                    }else if (myWeather.equals("Clear")){
                                        imageView.setBackgroundResource(R.drawable.clear);
                                    }

                                    Log.i("ID","ID " +par.getString("id"));
                                    Log.i("ID","MAIN " +par.getString("main"));
                                }

                                String main = jsonObject.getString("main");
                                Log.i("MAIN","MAIN"+main);
                                JSONObject mainTemp = new  JSONObject(main);
                                String temp = mainTemp.getString("temp");
                                Log.i("TEMPERATURE","TEMPERATURE "+temp );
                                temprature.setText("Temperature : "+temp);





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ERROR","VOLLY ERROR"+error);
                            Toast.makeText(MainActivity.this, "Cannot get the weather..Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    });

                    MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);





                }
            });

        }



    }
}
