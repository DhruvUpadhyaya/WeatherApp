package com.example.dhruvupadhyaya.weatherapp;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.cityId);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (city.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent =new Intent(MainActivity.this,ShowTemperatureActivity.class);

                    intent.putExtra("CITYNAME",city.getText().toString());
                    startActivity(intent);


                }

            }
        });




    }
}
