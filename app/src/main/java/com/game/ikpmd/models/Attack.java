package com.game.ikpmd.models;

import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
import com.game.ikpmd.models.units.Swordsman;

public class Attack {
    boolean arrived;
    int uniqueAttackIdentifier;
    int targetCityUniqueId;
    int originCityUniqueId;
    Swordsman swordsman;
    Archer archer;
    Horseman horseman;
    long arrivaltime;

    public Attack(int targetCityUniqueId, int originCityUniqueId, Swordsman swordsman, Archer archer, Horseman horseman, long arrivaltime){
        this.arrived = false;
        this.targetCityUniqueId = targetCityUniqueId;
        this.originCityUniqueId = originCityUniqueId;
        this.swordsman = swordsman;
        this.archer = archer;
        this.horseman = horseman;
        this.arrivaltime = arrivaltime;
    }

    public Attack(boolean arrived, int uniqueAttackIdentifier, int targetCityUniqueId, int originCityUniqueId, Swordsman swordsman, Archer archer, Horseman horseman, long arrivaltime){
        this.arrived = arrived;
        this.targetCityUniqueId = targetCityUniqueId;
        this.originCityUniqueId = originCityUniqueId;
        this.swordsman = swordsman;
        this.archer = archer;
        this.horseman = horseman;
        this.arrivaltime = arrivaltime;
        this.uniqueAttackIdentifier = uniqueAttackIdentifier;
    }

    public Attack(int uniqueAttackIdentifier){
        this.uniqueAttackIdentifier  = uniqueAttackIdentifier;
    }

    public int getTargetCityUniqueId() {
        return targetCityUniqueId;
    }

    public int getOriginCityUniqueId() {
        return originCityUniqueId;
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

    public long getArrivaltime() {
        return arrivaltime;
    }

    public void setUniqueAttackIdentifier(int uniqueAttackIdentifier) {
        this.uniqueAttackIdentifier = uniqueAttackIdentifier;
    }

    public int getUniqueAttackIdentifier() {
        return uniqueAttackIdentifier;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }
}
