package com.game.ikpmd.models.units;

public class Archer implements Unit{
    int amount;
    long cost;
    int defence;
    int offence;

    public Archer(){
        this.amount = 0;
        this.defence = 11;
        this.offence = 10;
        this.cost = 75;
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

    public void addArcher(){
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
