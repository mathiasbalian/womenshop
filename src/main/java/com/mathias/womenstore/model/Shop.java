package com.mathias.womenstore.model;

// This is a singleton class, as we only have one shop.
public class Shop {

    private double income;
    private double cost;
    private double capital;

    public Shop(double income, double cost, double capital) {
        this.income = income;
        this.cost = cost;
        this.capital = capital;
    }

    public Shop() {

    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }
}
