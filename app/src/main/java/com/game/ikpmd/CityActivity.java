package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.ikpmd.database.DatabaseHelper;
import com.game.ikpmd.firebaseConnector.FirebaseConnector;
import com.game.ikpmd.list.AttackListActivity;
import com.game.ikpmd.list.CityListActivity;
import com.game.ikpmd.list.CityListAdapter;
import com.game.ikpmd.models.Attack;
import com.game.ikpmd.models.City;
import com.game.ikpmd.models.buildings.Goldmine;
import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
import com.game.ikpmd.models.units.Swordsman;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

import java.util.Random;

public class CityActivity extends AppCompatActivity {
    private FirebaseConnector firebaseConnector;
    private boolean userHasCity = false;
    DatabaseHelper databaseHelper;
    City currentCity;
    boolean underattack;
    boolean attacking;
    ArrayList<Attack> incomingAttacks;
    ArrayList<Attack> outgoingAttacks;
    ArrayList<Attack> allAttacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        // FirebaseConnector setup
        firebaseConnector = new FirebaseConnector();
        firebaseConnector.connect();

        // DatabaseHelper setup
        databaseHelper = DatabaseHelper.getHelper(this);

        // Get username from intent
        String username = getIntent().getStringExtra("username");

        getAllCities(username);
        createListeners();
        getAttacks();
    }

    public void checkForAttacks(ArrayList<Attack> attacks){
        underattack = false;
        attacking = false;

        for (int i = 0; i < attacks.size(); i++){
            if (attacks.get(i).getTargetCityUniqueId() == Integer.parseInt(currentCity.getUniqueIdentifier()) && !attacks.get(i).isArrived()){
                underattack = true;
                incomingAttacks.add(attacks.get(i));
                allAttacks.add(attacks.get(i));
            }
            if (attacks.get(i).getOriginCityUniqueId() == Integer.parseInt(currentCity.getUniqueIdentifier()) && !attacks.get(i).isArrived()){
                attacking = true;
                outgoingAttacks.add(attacks.get(i));
                allAttacks.add(attacks.get(i));
            }
        }


    }

    private void getAttacks(){
        firebaseConnector.getAttacks(this);
    }

    private void getAllCities(String username){
        firebaseConnector.checkCitiesForUser(username, this);
    }

    public void checkIfUserHasCity(String username, ArrayList<City> cities){
        for (int i = 0; i < cities.size(); i++){
            if (cities.get(i).getOwner().equals(username)){
                userHasCity = true;
                currentCity = cities.get(i);
            }
        }

        addCitiesToLocalDb(cities);

        if (!userHasCity){
            currentCity = createCity(username);
        }
        setPageVariables();
    }

    public void setPageVariables(){

        currentCity.getGoldmine().collect();
        firebaseConnector.updateCityInFirebase(currentCity);

        TextView cityNameText = findViewById(R.id.cityNameText);
        cityNameText.setText(currentCity.getName());
        cityNameText.setTextSize(20f);

        TextView buildingsText = findViewById(R.id.buildingsText);
        buildingsText.setTextSize(17f);
        TextView resourcesText = findViewById(R.id.resourcesText);
        resourcesText.setTextSize(17f);

        TextView currentGoldText = findViewById(R.id.currentGoldText);
        currentGoldText.setText("Gold: "+currentCity.getGoldmine().getGold());
    }

    public City createCity(String username){
        Random random = new Random();
        int xAxisPosition = random.nextInt(100);
        int yAxisPosition = random.nextInt(100);

        City city = new City(username, "city of "+username, xAxisPosition, yAxisPosition, new Goldmine(), new Swordsman(), new Archer(), new Horseman());

        String[] columnString = new String[]{
                "uniqueIdentifier"
        };
        Cursor cursor = databaseHelper.query("CityTable", columnString, null, null, null, null, null);

        cursor.moveToFirst();

        int highestIdentifier = 0;

        for (int i = 0; i < cursor.getCount(); i++){

            int currentInt = Integer.parseInt(cursor.getString(0));
            Log.d("newcitylog4", ""+currentInt);

            if (highestIdentifier < Integer.parseInt(cursor.getString(0))){
                highestIdentifier = currentInt;
            }
            cursor.moveToNext();
        }

        city.setUniqueIdentifier(""+(highestIdentifier + 1));

        databaseHelper.insertCityIntoSQLiteDatabase(city);

        // firebaseconnector add city into firebase
        firebaseConnector.updateCityInFirebase(city);

        return city;
    }

    public void addCitiesToLocalDb(ArrayList<City> cities){

        databaseHelper.onUpgrade(databaseHelper.mSQLDB, 0, 0);

        for (int i = 0; i < cities.size(); i++){
            databaseHelper.insertCityIntoSQLiteDatabase(cities.get(i));
        }
    }

    public void moveToCityList(){
        Intent intent = new Intent(CityActivity.this, CityListActivity.class);
        intent.putExtra("city", new Gson().toJson(currentCity));
        startActivity(intent);
    }

    public void moveToBarracks(){
        Intent intent = new Intent(CityActivity.this, BarracksActivity.class);
        intent.putExtra("city", new Gson().toJson(currentCity));
        startActivity(intent);
    }

    private void createListeners(){
        ImageButton loginButton = (ImageButton) findViewById(R.id.openCityListButton);
        ImageButton barracksButton = (ImageButton) findViewById(R.id.barracksButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                moveToCityList();
            }
        });

        barracksButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                moveToBarracks();
            }
        });
    }

    public void moveToAttackList(){
        Intent intent = new Intent(CityActivity.this, AttackListActivity.class);
        startActivity(intent);
    }
}
