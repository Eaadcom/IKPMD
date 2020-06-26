package com.game.ikpmd.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.game.ikpmd.R;
import com.game.ikpmd.database.DatabaseHelper;
import com.game.ikpmd.models.Attack;
import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
import com.game.ikpmd.models.units.Swordsman;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AttackListActivity extends AppCompatActivity {
    private ListView mListView;
    private AttackListAdapter mAdapter;
    DatabaseHelper databaseHelper;
    ArrayList<Attack> attacks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_list);

        loadAttacks();

        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                                                 if (!attacks.get(position).getUniqueIdentifier().equals(currentCity.getUniqueIdentifier())){
//                                                     Intent intent = new Intent(CityListActivity.this, AttackActivity.class);
//                                                     intent.putExtra("enemyCity", new Gson().toJson(cities.get(position)));
//                                                     intent.putExtra("currentCity", new Gson().toJson(currentCity));
//                                                     startActivity(intent);
//                                                 } else {
//                                                     Toast t = Toast.makeText(CityListActivity.this,"Your own city!" ,Toast.LENGTH_SHORT);
//                                                     t.show();
//                                                 }
                                             }
                                         }
        );

        mAdapter = new AttackListAdapter(AttackListActivity.this, 0, attacks);
        mListView.setAdapter(mAdapter);
    }

    private void loadAttacks(){
        databaseHelper = DatabaseHelper.getHelper(this);

        String[] columnString = new String[]{
                "targetCityUniqueId",
                "originCityUniqueId",
                "aSwordsman",
                "aArcher",
                "aHorseman",
                "arrivaltime",
                "uniqueAttackIdentifier",
                "arrived"
        };

        Cursor cursor = databaseHelper.query("AttackTable", columnString, null, null, null, null, null);

        Gson gson = new Gson();

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++){
            Attack attack = new Attack(Boolean.parseBoolean(cursor.getString(7)),  Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)), gson.fromJson(cursor.getString(2), Swordsman.class),
                    gson.fromJson(cursor.getString(3), Archer.class), gson.fromJson(cursor.getString(4), Horseman.class), Integer.parseInt(cursor.getString(5)));
            attacks.add(attack);
            cursor.moveToNext();
        }
    }
}