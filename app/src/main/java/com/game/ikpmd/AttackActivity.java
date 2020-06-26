package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.ikpmd.firebaseConnector.FirebaseConnector;
import com.game.ikpmd.models.Attack;
import com.game.ikpmd.models.City;
import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
import com.game.ikpmd.models.units.Swordsman;
import com.google.gson.Gson;

import java.time.Instant;

public class AttackActivity extends AppCompatActivity {
    City currentCity;
    City enemyCity;
    FirebaseConnector firebaseConnector;
    long distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);

        // Get city object from intent
        Gson gson = new Gson();
        currentCity = gson.fromJson(getIntent().getStringExtra("currentCity"), City.class);
        enemyCity = gson.fromJson(getIntent().getStringExtra("enemyCity"), City.class);

        // FirebaseConnector setup
        firebaseConnector = new FirebaseConnector();
        firebaseConnector.connect();

        setupVariables();
        setupListeners();
    }

    private void setupVariables(){

        int xcoor = currentCity.getxAxisPosition() - enemyCity.getxAxisPosition();
        int ycoor = currentCity.getyAxisPosition() - enemyCity.getyAxisPosition();

        xcoor = xcoor * xcoor;
        ycoor = ycoor * ycoor;

        distance = (long) Math.sqrt(xcoor + ycoor);

        TextView targetCityName = findViewById(R.id.targetCityName);
        targetCityName.setText(enemyCity.getName());

        TextView cityOwner = findViewById(R.id.cityOwner);
        cityOwner.setText("City is owned by: "+enemyCity.getOwner());

        TextView targetXcoor = findViewById(R.id.target_xCoor);
        targetXcoor.setText("x: "+enemyCity.getxAxisPosition());

        TextView targetYcoor = findViewById(R.id.target_yCoor);
        targetYcoor.setText("x: "+enemyCity.getyAxisPosition());

        TextView targetDistance = findViewById(R.id.target_distance);
        targetDistance.setText("Distance: "+(distance * 5)+" seconden");
    }

    private void setupListeners(){
        Button attackButton = (Button) findViewById(R.id.sendAttack);

        attackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView swordGoonField = findViewById(R.id.swordGoonAmount);
                TextView bowGoonField = findViewById(R.id.bowGoonAmount);
                TextView horseGoonField = findViewById(R.id.horseGoonAmount);

                int swordGoonAmount = Integer.parseInt(swordGoonField.getText().toString());
                int bowGoonAmount = Integer.parseInt((bowGoonField.getText().toString()));
                int horseGoonAmount = Integer.parseInt(horseGoonField.getText().toString());

                if (swordGoonAmount > currentCity.getSwordsman().getAmount()){
                    swordGoonAmount = currentCity.getSwordsman().getAmount();
                }
                if (bowGoonAmount > currentCity.getArcher().getAmount()){
                    bowGoonAmount = currentCity.getArcher().getAmount();
                }
                if (horseGoonAmount > currentCity.getHorseman().getAmount()){
                    horseGoonAmount = currentCity.getHorseman().getAmount();
                }

                Swordsman swordsman = new Swordsman();
                swordsman.setAmount(swordGoonAmount);

                Archer archer = new Archer();
                archer.setAmount(bowGoonAmount);

                Horseman horseman = new Horseman();
                horseman.setAmount(horseGoonAmount);

                long arrivaltime = Instant.now().getEpochSecond();
                arrivaltime = arrivaltime + (5 * distance);

                currentCity.getSwordsman().reduceByAmount(swordGoonAmount);
                currentCity.getArcher().reduceByAmount(bowGoonAmount);
                currentCity.getHorseman().reduceByAmount(horseGoonAmount);

                firebaseConnector.createAttack(new Attack(Integer.parseInt(enemyCity.getUniqueIdentifier()), Integer.parseInt(currentCity.getUniqueIdentifier()),
                        swordsman, archer, horseman, arrivaltime));
                firebaseConnector.updateCityInFirebase(currentCity);
            }
        });
    }
}