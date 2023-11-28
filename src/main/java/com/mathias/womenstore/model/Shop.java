package com.mathias.womenstore.model;

// This is a singleton class, as we only have one shop.
public class Shop {

    public double income;
    public double cost;
    public double capital;
    private static Shop instance;

    private Shop(double income, double cost, double capital) {
        this.income = income;
        this.cost = cost;
        this.capital = capital;
    }

    public static Shop getInstance(double income, double cost, double capital) {
        if (instance == null) {
            instance = new Shop(income, cost, capital);
        }
        return instance;
    }
}
