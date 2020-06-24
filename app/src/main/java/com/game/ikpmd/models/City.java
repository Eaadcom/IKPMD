package com.game.ikpmd.models;

import com.game.ikpmd.models.units.Unit;

import java.util.ArrayList;

public class City {
    private String owner;
    private int xAxisPosition;
    private int yAxisPosition;
    private ArrayList<Unit> units;

    public String getOwner() {
        return owner;
    }
}
