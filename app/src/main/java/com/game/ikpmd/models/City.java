package com.game.ikpmd.models;

import com.game.ikpmd.models.buildings.Building;
import com.game.ikpmd.models.buildings.Goldmine;
import com.game.ikpmd.models.units.Swordsman;
import com.game.ikpmd.models.units.Unit;

import java.io.Serializable;
import java.util.ArrayList;

public class City implements Serializable {
    private String uniqueIdentifier;
    private String owner;
    private String name;
    private int xAxisPosition;
    private int yAxisPosition;
    private ArrayList<Unit> units;
    private ArrayList<Building> buildings;
    private Goldmine goldmine;
    private Swordsman swordsman;

    public City(String owner, String name, int xAxisPosition, int yAxisPosition, Goldmine goldmine, Swordsman swordsman){
        this.owner = owner;
        this.name = name;
        this.xAxisPosition = xAxisPosition;
        this.yAxisPosition = yAxisPosition;
        //buildings = new ArrayList<>();
        this.goldmine = goldmine;
        this.swordsman = swordsman;
    }

    public City(String owner, String name, int xAxisPosition, int yAxisPosition){
        this.owner = owner;
        this.name = name;
        this.xAxisPosition = xAxisPosition;
        this.yAxisPosition = yAxisPosition;
        //buildings = new ArrayList<>();
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getxAxisPosition() {
        return xAxisPosition;
    }

    public int getyAxisPosition() {
        return yAxisPosition;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public String getName() {
        return name;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void addBuilding(Building building){
        buildings.add(building);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Goldmine getGoldmine() {
        return goldmine;
    }

    public Swordsman getSwordsman() {
        return swordsman;
    }
}
