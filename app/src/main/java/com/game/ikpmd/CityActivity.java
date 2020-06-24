package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.game.ikpmd.firebaseConnector.FirebaseConnector;
import com.game.ikpmd.models.City;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {
    private FirebaseConnector firebaseConnector;
    private boolean userHasCity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        // FirebaseConnector setup
        firebaseConnector = new FirebaseConnector();
        firebaseConnector.connect();

        // Get username from intent
        String username = getIntent().getStringExtra("username");

        getAllCities(username);
    }

    private void getAllCities(String username){
        firebaseConnector.checkCitiesForUser(username, this);
    }

    public void checkIfUserHasCity(String username, ArrayList<City> cities){
        for (int i = 0; i < cities.size(); i++){
            if (cities.get(i).getOwner().equals(username)){
                userHasCity = true;
                break;
            }
        }

        if (!userHasCity){
            
        }
    }
}