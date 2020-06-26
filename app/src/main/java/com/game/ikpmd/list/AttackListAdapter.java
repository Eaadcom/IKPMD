package com.game.ikpmd.list;

import androidx.annotation.NonNull;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.game.ikpmd.R;
import com.game.ikpmd.database.DatabaseHelper;
import com.game.ikpmd.models.Attack;
import com.game.ikpmd.models.City;
import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
import com.game.ikpmd.models.units.Swordsman;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AttackListAdapter extends ArrayAdapter<Attack> {
    Attack attack;
    ArrayList<City> involvedCities = new ArrayList<>();
    DatabaseHelper databaseHelper;
    String originCityName;
    String targetCityName;

    public AttackListAdapter(@NonNull Context context, int resource, List<Attack> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AttackListAdapter.ViewHolder vh;
        attack = getItem(position);
        getCityNames();

        if (convertView == null ) {
            vh = new AttackListAdapter.ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.activity_attack_list_adapter, parent, false);
            vh.originCity = (TextView) convertView.findViewById(R.id.originCity);
            vh.targetCity = (TextView) convertView.findViewById(R.id.targetCity);
            vh.swordmanAmount = (TextView) convertView.findViewById(R.id.swordsmanAmount);
            vh.archerAmount = (TextView) convertView.findViewById(R.id.archerAmount);
            vh.horsemanAmount = (TextView) convertView.findViewById(R.id.horsemanAmount);
            convertView.setTag(vh);
        } else {
            vh = (AttackListAdapter.ViewHolder) convertView.getTag();
        }

        vh.originCity.setText((CharSequence) "from: "+originCityName);
        vh.targetCity.setText((CharSequence) "to: "+targetCityName);
        vh.swordmanAmount.setText((CharSequence) ("swordsmen: "+attack.getSwordsman().getAmount()));
        vh.archerAmount.setText((CharSequence) ("archers: "+attack.getArcher().getAmount()));
        vh.horsemanAmount.setText((CharSequence) ("horsemen: "+attack.getHorseman().getAmount()));
        return convertView;
    }

    private static class ViewHolder {
        TextView originCity;
        TextView targetCity;
        TextView swordmanAmount;
        TextView archerAmount;
        TextView horsemanAmount;
    }

    private void getCityNames(){
        databaseHelper = DatabaseHelper.getHelper();

        String[] columnString = new String[]{
                "uniqueIdentifier",
                "name",
        };

        Cursor cursor = databaseHelper.query("CityTable", columnString, null, null, null, null, null);

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++){
            City city = new City(cursor.getString(0), cursor.getString(1));
            if (Integer.parseInt(city.getUniqueIdentifier()) == attack.getOriginCityUniqueId()){
                originCityName = city.getName();
            } if (Integer.parseInt(city.getUniqueIdentifier()) == attack.getTargetCityUniqueId()){
                targetCityName = city.getName();
            }
            cursor.moveToNext();
        }
    }
}