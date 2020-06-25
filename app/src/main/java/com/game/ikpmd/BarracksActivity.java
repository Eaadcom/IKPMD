package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.ikpmd.firebaseConnector.FirebaseConnector;
import com.game.ikpmd.models.City;
import com.google.gson.Gson;

public class BarracksActivity extends AppCompatActivity {
    City currentCity;
    private FirebaseConnector firebaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barracks);

        // FirebaseConnector setup
        firebaseConnector = new FirebaseConnector();
        firebaseConnector.connect();

        // Get city object from intent
        Gson gson = new Gson();
        currentCity = gson.fromJson(getIntent().getStringExtra("city"), City.class);

        // Setup class
        setPageVariables();
        createListeners();
    }

    public void setPageVariables(){

//        currentCity.getGoldmine().collect();
//        firebaseConnector.updateCityInFirebase(currentCity);

        TextView swordsmanOffence = findViewById(R.id.swordsmanOffence);
        swordsmanOffence.setText("Offence: "+currentCity.getSwordsman().getOffence());

        TextView swordsmanDefence = findViewById(R.id.swordsmanDefence);
        swordsmanDefence.setText("Defence: "+currentCity.getSwordsman().getDefence());

        TextView swordsmanCost = findViewById(R.id.swordsmanCost);
        swordsmanCost.setText("Cost: "+currentCity.getSwordsman().getCost()+" g");

        TextView swordsmanOwned = findViewById(R.id.swordsmanOwned);
        swordsmanOwned.setText("Owned: "+currentCity.getSwordsman().getAmount());
    }

    private void createListeners(){
        Button recruitSwordsmanButton = (Button) findViewById(R.id.recruitSwordsmanButton);

        recruitSwordsmanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                long gold = currentCity.getGoldmine().getGold();
                long unitPrice = currentCity.getSwordsman().getCost();
                if (gold > unitPrice){
                    currentCity.getGoldmine().subtractGold(unitPrice);
                    currentCity.getSwordsman().addSwordsman();
                    firebaseConnector.updateCityInFirebase(currentCity);
                }
            }
        });
    }
}