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

import java.time.Instant;
import java.util.ArrayList;

import java.util.Random;

public class CityActivity extends AppCompatActivity {
    City targetCity;
    private FirebaseConnector firebaseConnector;
    private boolean userHasCity = false;
    boolean getAttacks = true;
    DatabaseHelper databaseHelper;
    City currentCity;
    boolean underattack;
    boolean attacking;
    boolean citiesLoaded;
    ArrayList<Attack> incomingAttacks = new ArrayList<>();
    ArrayList<Attack> outgoingAttacks = new ArrayList<>();
    ArrayList<Attack> allAttacks = new ArrayList<>();
    private ArrayList<City> allCities = new ArrayList<>();

    int targetCityId;
    int attackPowerSwordsman;
    int attackPowerArcher;
    int attackPowerHorseman;
    int totalAttackPower;
    int totalAttackPower2;
    int defencePowerSwordsman;
    int defencePowerArcher;
    int defencePowerHorseman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        // FirebaseConnector setup
        firebaseConnector = new FirebaseConnector();
        firebaseConnector.connect();

        // DatabaseHelper setup
        databaseHelper = DatabaseHelper.getHelper(this);
        databaseHelper.onUpgrade(databaseHelper.mSQLDB, 0, 0);
        citiesLoaded = false;

        // Get username from intent
        String username = getIntent().getStringExtra("username");

        getAllCities(username);
        createListeners();
    }

    private void calculateAttackOutcome(Attack attack){
        targetCityId = attack.getTargetCityUniqueId();

        attackPowerSwordsman = attack.getSwordsman().getOffence() * attack.getSwordsman().getAmount();
        attackPowerArcher = attack.getArcher().getOffence() * attack.getArcher().getAmount();
        attackPowerHorseman = attack.getHorseman().getOffence() * attack.getHorseman().getAmount();

        totalAttackPower = attackPowerSwordsman + attackPowerArcher + attackPowerHorseman;

        Log.d("", ""+allCities.size());

        for (int i = 0; i < allCities.size(); i++){
            if (Integer.parseInt(allCities.get(i).getUniqueIdentifier()) == targetCityId){
                targetCity = allCities.get(i);
            }
        }

        defencePowerSwordsman = attack.getSwordsman().getDefence() * attack.getSwordsman().getAmount();
        defencePowerArcher = attack.getArcher().getDefence() * attack.getArcher().getAmount();
        defencePowerHorseman = attack.getHorseman().getDefence() * attack.getHorseman().getAmount();

        // HorsemanDefends
        if (defencePowerHorseman - totalAttackPower < 0){
            defencePowerHorseman = 0;
        } else {
            totalAttackPower2 = totalAttackPower = defencePowerHorseman;
            defencePowerHorseman = defencePowerHorseman - totalAttackPower;
            totalAttackPower = totalAttackPower2;
        }

        // SwordsmanDefends
        if (defencePowerSwordsman - totalAttackPower < 0){
            defencePowerSwordsman = 0;
        } else {
            totalAttackPower2 = totalAttackPower - defencePowerSwordsman;
            defencePowerSwordsman = defencePowerSwordsman - totalAttackPower;
            totalAttackPower = totalAttackPower2;
        }

        // ArcherDefends
        if (defencePowerArcher - totalAttackPower < 0){
            defencePowerArcher = 0;
        } else {
            totalAttackPower2 = totalAttackPower - defencePowerArcher;
            defencePowerArcher = defencePowerArcher - totalAttackPower;
            totalAttackPower = totalAttackPower2;
        }

        targetCity.getHorseman().setAmount( (int) (defencePowerHorseman / targetCity.getHorseman().getDefence()) * 10);
        targetCity.getArcher().setAmount( (int) (defencePowerArcher / targetCity.getArcher().getDefence()) * 10);
        targetCity.getSwordsman().setAmount( (int) (defencePowerSwordsman / targetCity.getSwordsman().getDefence()) * 10);

        attack.setArrived(true);
        firebaseConnector.updateCityInFirebase(targetCity);
        firebaseConnector.updateAttackInFirebase(attack);
    }

    public void checkForAttacks(ArrayList<Attack> attacks){
        underattack = false;
        attacking = false;

        for (int i = 0; i < attacks.size(); i++){
            if (attacks.get(i).getTargetCityUniqueId() == Integer.parseInt(currentCity.getUniqueIdentifier()) && !attacks.get(i).isArrived()){
                if(attacks.get(i).getArrivaltime() <= Instant.now().getEpochSecond()){
                    calculateAttackOutcome(attacks.get(i));
                } else {
                    underattack = true;
                    incomingAttacks.add(attacks.get(i));
                    allAttacks.add(attacks.get(i));
                }
            }
            if (attacks.get(i).getOriginCityUniqueId() == Integer.parseInt(currentCity.getUniqueIdentifier()) && !attacks.get(i).isArrived()){
                if(attacks.get(i).getArrivaltime() <= Instant.now().getEpochSecond()){
                    calculateAttackOutcome(attacks.get(i));
                } else {
                    attacking = true;
                    incomingAttacks.add(attacks.get(i));
                    allAttacks.add(attacks.get(i));
                }
            }
        }

        addAttacksToLocalDb();
    }

    public void addAttacksToLocalDb(){

        for (int i = 0; i < allAttacks.size(); i++){
            databaseHelper.insertAttackIntoSQLiteDatabase(allAttacks.get(i));
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

        allCities = cities;
        if (getAttacks){
            getAttacks();
        }
        getAttacks = false;
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
        if (!citiesLoaded){
            for (int i = 0; i < cities.size(); i++){
                databaseHelper.insertCityIntoSQLiteDatabase(cities.get(i));
            }
        }
        citiesLoaded = true;
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
        Button attackListButton = (Button) findViewById(R.id.attackListButton);

        attackListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                moveToAttackList();
            }
        });

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
