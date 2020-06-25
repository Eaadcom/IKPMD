package com.game.ikpmd.list;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.game.ikpmd.R;
import com.game.ikpmd.database.DatabaseHelper;
import com.game.ikpmd.models.City;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private ListView mListView;
    private CityListAdapter mAdapter;
    private List<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                 Toast t = Toast.makeText(CityListActivity.this,"Click" + position,Toast.LENGTH_LONG);
                                                 t.show();
                                             }
                                         }
        );

        databaseHelper = DatabaseHelper.getHelper(this);

        String[] columnString = new String[]{
                "owner",
                "name",
                "xAxisPosition",
                "yAxisPosition"
        };

        Cursor cursor = databaseHelper.query("CityTable", columnString, null, null, null, null, null);

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            City city = new City(cursor.getString(0),cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));
            cities.add(city);
            cursor.moveToNext();
        }

        mAdapter = new CityListAdapter(CityListActivity.this, 0, cities);
        mListView.setAdapter(mAdapter);
    }
}