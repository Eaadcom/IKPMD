package com.game.ikpmd.models.units;

public class Swordsman implements Unit{
    int amount;
    long cost;
    int defence;
    int offence;

    public Swordsman(){
        this.amount = 0;
        this.defence = 14;
        this.offence = 8;
        this.cost = 85;
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

    public void addSwordsman(){
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
