package com.game.ikpmd.models.buildings;

import java.io.Serializable;
import java.time.Instant;

public class Goldmine implements Building, Serializable {
    String name;
    private long gold;
    int buildingLevel;
    long goldPerSecond;
    long lastcollected;

    public Goldmine(){
        lastcollected = Instant.now().getEpochSecond();

        this.name = "Goldmine";
        this.buildingLevel = 1;
        this.goldPerSecond = 1;
    }

    public Goldmine(String name, int buildingLevel, int goldPerMinute){
        this.name = name;
        this.buildingLevel = buildingLevel;
        this.goldPerSecond = goldPerMinute;
    }

    public void upgradeBuilding(){

    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public long getGoldPerSecond() {
        return goldPerSecond;
    }

    public long getGold() {
        return gold;
    }

    public String getName() {
        return name;
    }

    public long getLastcollected() {
        return lastcollected;
    }

    public void collect(){
        long currentTime = Instant.now().getEpochSecond();
        long passedtime = currentTime - lastcollected;
        gold = gold + (passedtime * goldPerSecond);
        lastcollected = currentTime;
    }

    public void subtractGold(long price){
        gold = gold - price;
    }
}
