package com.game.ikpmd.list;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.game.ikpmd.R;
import com.game.ikpmd.models.City;

import java.util.List;

public class CityListAdapter extends ArrayAdapter<City> {
    City city;

    public CityListAdapter(@NonNull Context context, int resource, List<City> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityListAdapter.ViewHolder vh;

        createListeners();

        if (convertView == null ) {
            vh = new CityListAdapter.ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.view_content_row, parent, false);
            vh.name = (TextView) convertView.findViewById(R.id.subject_name);
            vh.owner = (TextView) convertView.findViewById(R.id.subject_owner);
            vh.xCoor = (TextView) convertView.findViewById(R.id.subject_xCoor);
            vh.yCoor = (TextView) convertView.findViewById(R.id.subject_yCoor);
            convertView.setTag(vh);
        } else {
            vh = (CityListAdapter.ViewHolder) convertView.getTag();
        }
        city = getItem(position);
        vh.name.setText((CharSequence) city.getName());
        vh.owner.setText((CharSequence) "City is owned by: "+city.getOwner());
        vh.xCoor.setText((CharSequence) "x: "+String.valueOf(city.getxAxisPosition()));
        vh.yCoor.setText((CharSequence) "y: "+String.valueOf(city.getyAxisPosition()));
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView owner;
        TextView xCoor;
        TextView yCoor;
    }

    private void createListeners(){
//        Button attackButton = (Button) itemView.findViewById(R.id.attackButton);
//
//        attackButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Log.d("test", city.getName());
//            }
//        });
    }
}