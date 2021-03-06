package com.example.bryan.hereheroemergency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Button button_Earthquake = findViewById(R.id.button_InformationActivity_Earthquake);
        Button button_Tsunami = findViewById(R.id.button_InformationActivity_Tsunami);
        Button button_CarAccident = findViewById(R.id.button_InformationActivity_CarAccident);
        Button button_CPR = findViewById(R.id.button_InformationActivity_CPR);
        Button button_Terrorist = findViewById(R.id.button_InformationActivity_Terrorist);
        Button button_HouseFire = findViewById(R.id.button_informationActivity_HouseFire);

        button_Earthquake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),EarthquakeActivity.class);
                startActivity(intent);
            }
        });

        button_Tsunami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),TsunamiActivity.class);
                startActivity(intent);
            }
        });

        button_CarAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),CarAccidentActivity.class);
                startActivity(intent);

            }
        });

        button_CPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),CPRActivity.class);
                startActivity(intent);
            }
        });

        button_Terrorist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), TerroristActivity.class);
                startActivity(intent);
            }
        });

        button_HouseFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),HouseFireActivity.class);
                startActivity(intent);
            }
        });
    }

}
