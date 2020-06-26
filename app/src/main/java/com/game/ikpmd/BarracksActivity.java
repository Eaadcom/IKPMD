package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView barracksTitle = findViewById(R.id.barracksTitle);
        barracksTitle.setTextSize(20f);

        // Swordsman Variables
        TextView swordsmanOffence = findViewById(R.id.swordsmanOffence);
        swordsmanOffence.setText("Offence: "+currentCity.getSwordsman().getOffence());

        TextView swordsmanDefence = findViewById(R.id.swordsmanDefence);
        swordsmanDefence.setText("Defence: "+currentCity.getSwordsman().getDefence());

        TextView swordsmanCost = findViewById(R.id.swordsmanCost);
        swordsmanCost.setText("Cost: "+currentCity.getSwordsman().getCost()+" g");

        TextView swordsmanOwned = findViewById(R.id.swordsmanOwned);
        swordsmanOwned.setText("Owned: "+currentCity.getSwordsman().getAmount());

        // Archer Variables
        TextView archerOffence = findViewById(R.id.archerOffence);
        archerOffence.setText("Offence: "+currentCity.getArcher().getOffence());

        TextView archerDefence = findViewById(R.id.archerDefence);
        archerDefence.setText("Defence: "+currentCity.getArcher().getDefence());

        TextView archerCost = findViewById(R.id.archerCost);
        archerCost.setText("Cost: "+currentCity.getArcher().getCost()+" g");

        TextView archerOwned = findViewById(R.id.archerOwned);
        archerOwned.setText("Owned: "+currentCity.getArcher().getAmount());

        // Horseman Variables
        TextView horsemanOffence = findViewById(R.id.horsemanOffence);
        horsemanOffence.setText("Offence: "+currentCity.getHorseman().getOffence());

        TextView horsemanDefence = findViewById(R.id.horsemanDefence);
        horsemanDefence.setText("Defence: "+currentCity.getHorseman().getDefence());

        TextView horsemanCost = findViewById(R.id.horsemanCost);
        horsemanCost.setText("Cost: "+currentCity.getHorseman().getCost()+" g");

        TextView horsemanOwned = findViewById(R.id.horsemanOwned);
        horsemanOwned.setText("Owned: "+currentCity.getHorseman().getAmount());

        // Barracks Gold
        TextView barracksGoldView = findViewById(R.id.barracksGoldView);
        barracksGoldView.setText("Gold: "+currentCity.getGoldmine().getGold());
    }

    private void createListeners(){
        Button recruitSwordsmanButton = (Button) findViewById(R.id.recruitSwordsmanButton);
        Button recruitArcherButton = (Button) findViewById(R.id.recruitArcherButton);
        Button recruitHorsemanButton = (Button) findViewById(R.id.recruitHorsemanButton);
        ImageButton unitDataVisualButton = (ImageButton) findViewById(R.id.unitDataVisualButton);

        recruitSwordsmanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                long gold = currentCity.getGoldmine().getGold();
                long unitPrice = currentCity.getSwordsman().getCost();
                if (gold > unitPrice){
                    currentCity.getGoldmine().subtractGold(unitPrice);
                    currentCity.getSwordsman().addSwordsman();

                    generateToast("Swordsman");

                    setPageVariables();
                    firebaseConnector.updateCityInFirebase(currentCity);
                }
            }
        });

        recruitArcherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                long gold = currentCity.getGoldmine().getGold();
                long unitPrice = currentCity.getArcher().getCost();
                if (gold > unitPrice){
                    currentCity.getGoldmine().subtractGold(unitPrice);
                    currentCity.getArcher().addArcher();

                    generateToast("Archer");

                    setPageVariables();
                    firebaseConnector.updateCityInFirebase(currentCity);
                }
            }
        });

        recruitHorsemanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                long gold = currentCity.getGoldmine().getGold();
                long unitPrice = currentCity.getHorseman().getCost();
                if (gold > unitPrice){
                    currentCity.getGoldmine().subtractGold(unitPrice);
                    currentCity.getHorseman().addHorseman();

                    generateToast("Horseman");

                    setPageVariables();
                    firebaseConnector.updateCityInFirebase(currentCity);
                }
            }
        });

        unitDataVisualButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BarracksActivity.this, UnitPiechartActivity.class);
                intent.putExtra("city", new Gson().toJson(currentCity));
                startActivity(intent);
            }
        });
    }

    private void generateToast(String unit){
        CharSequence text = "Recruited a "+unit;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}