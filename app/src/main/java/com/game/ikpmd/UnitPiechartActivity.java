package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.game.ikpmd.models.City;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UnitPiechartActivity extends AppCompatActivity {

    private City currentCity;
    private PieChart mChart;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_piechart);

        // Get city object from intent
        Gson gson = new Gson();
        currentCity = gson.fromJson(getIntent().getStringExtra("city"), City.class);

        // Setup Class
        setupPieChart();
        getUnitAmounts();
    }

    protected void setupPieChart(){
        mChart = (PieChart) findViewById(R.id.chart);
        Description d = new Description();
        d.setText("Units Piechart");
        d.setTextSize(20f);
        mChart.setDescription(d);
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mChart.setEntryLabelTextSize(10f);
    }

    private void setData(int swordsmen, int archers, int horsemen) {
        List<PieEntry> unitValues = new ArrayList<>();

        unitValues.add(new PieEntry(swordsmen, "Swordsmen"));
        unitValues.add(new PieEntry(archers, "Archers"));
        unitValues.add(new PieEntry(horsemen, "Horsemen"));


        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(244,81,30));
        colors.add(Color.rgb(235,0,0));
        colors.add(Color.rgb(67,160,71));

        PieDataSet dataSet = new PieDataSet(unitValues, "Units");
        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        mChart.setData(data);
        mChart.invalidate();
    }

    private void getUnitAmounts(){
        setData(
                currentCity.getSwordsman().getAmount(),
                currentCity.getArcher().getAmount(),
                currentCity.getHorseman().getAmount()
        );
    }
}