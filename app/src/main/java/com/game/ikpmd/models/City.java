package com.game.ikpmd.models;

import com.game.ikpmd.models.buildings.Building;
import com.game.ikpmd.models.buildings.Goldmine;
import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
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
    private Goldmine goldmine;
    private Swordsman swordsman;
    private Archer archer;
    private Horseman horseman;

    public City(String uniqueIdentifier, String owner, String name, int xAxisPosition, int yAxisPosition, Swordsman swordsman, Archer archer, Horseman horseman){
        this.uniqueIdentifier = uniqueIdentifier;
        this.owner = owner;
        this.name = name;
        this.xAxisPosition = xAxisPosition;
        this.yAxisPosition = yAxisPosition;
        this.swordsman = swordsman;
        this.archer = archer;
        this.horseman = horseman;
    }

    public City(String owner, String name, int xAxisPosition, int yAxisPosition, Goldmine goldmine, Swordsman swordsman, Archer archer, Horseman horseman){
        this.owner = owner;
        this.name = name;
        this.xAxisPosition = xAxisPosition;
        this.yAxisPosition = yAxisPosition;
        this.goldmine = goldmine;
        this.swordsman = swordsman;
        this.archer = archer;
        this.horseman = horseman;
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

    public String getName() {
        return name;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public Goldmine getGoldmine() {
        return goldmine;
    }

    public Swordsman getSwordsman() {
        return swordsman;
    }

    public Archer getArcher() {
        return archer;
    }

    public Horseman getHorseman() {
        return horseman;
    }
}
