package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.game.ikpmd.models.City;
import com.google.gson.Gson;

public class AttackActivity extends AppCompatActivity {
    City currentCity;
    City enemyCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);

        // Get city object from intent
        Gson gson = new Gson();
        currentCity = gson.fromJson(getIntent().getStringExtra("currentCity"), City.class);
        enemyCity = gson.fromJson(getIntent().getStringExtra("enemyCity"), City.class);
    }
}