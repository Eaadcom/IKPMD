package com.game.ikpmd.models.units;

public class Horseman implements Unit{
    int amount;
    long cost;
    int defence;
    int offence;

    public Horseman(){
        this.amount = 0;
        this.defence = 20;
        this.offence = 60;
        this.cost = 360;
    }

    public int getAmount() {
        return amount;
    }

    public int getDefence() {
        return defence;
    }

    public int getOffence() {
        return offence;
    }

    public void addHorseman(){
        amount = amount + 1;
    }

    public long getCost() {
        return cost;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void reduceByAmount(int amount){
        this.amount = this.amount - amount;
    }
}
