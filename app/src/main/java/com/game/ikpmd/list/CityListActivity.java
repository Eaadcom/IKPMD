package com.game.ikpmd.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.game.ikpmd.AttackActivity;
import com.game.ikpmd.BarracksActivity;
import com.game.ikpmd.CityActivity;
import com.game.ikpmd.R;
import com.game.ikpmd.database.DatabaseHelper;
import com.game.ikpmd.models.City;
import com.game.ikpmd.models.buildings.Goldmine;
import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
import com.game.ikpmd.models.units.Swordsman;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private ListView mListView;
    private CityListAdapter mAdapter;
    private List<City> cities = new ArrayList<>();
    private City currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        // Get city object from intent
        Gson gson = new Gson();
        currentCity = gson.fromJson(getIntent().getStringExtra("city"), City.class);

        loadCities();

        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                 if (!cities.get(position).getUniqueIdentifier().equals(currentCity.getUniqueIdentifier())){
                                                     Intent intent = new Intent(CityListActivity.this, AttackActivity.class);
                                                     intent.putExtra("enemyCity", new Gson().toJson(cities.get(position)));
                                                     intent.putExtra("currentCity", new Gson().toJson(currentCity));
                                                     startActivity(intent);
                                                 } else {
                                                     Toast t = Toast.makeText(CityListActivity.this,"Your own city!" ,Toast.LENGTH_SHORT);
                                                     t.show();
                                                 }
                                             }
                                         }
        );

        mAdapter = new CityListAdapter(CityListActivity.this, 0, cities);
        mListView.setAdapter(mAdapter);
    }

    private void loadCities(){
        databaseHelper = DatabaseHelper.getHelper(this);

        String[] columnString = new String[]{
                "uniqueIdentifier",
                "owner",
                "name",
                "xAxisPosition",
                "yAxisPosition",
                "swordsman",
                "archer",
                "horseman"
        };

        Cursor cursor = databaseHelper.query("CityTable", columnString, null, null, null, null, null);

        Gson gson = new Gson();
        currentCity = gson.fromJson(getIntent().getStringExtra("city"), City.class);

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            City city = new City(cursor.getString(0),cursor.getString(1),
                    cursor.getString(2),Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)), gson.fromJson(cursor.getString(5),
                    Swordsman.class),gson.fromJson(cursor.getString(6), Archer.class),
                    gson.fromJson(cursor.getString(7), Horseman.class));
            cities.add(city);
            cursor.moveToNext();
        }
    }
}