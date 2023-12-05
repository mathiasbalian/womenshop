package com.mathias.womenstore.model;

public abstract class Product implements Discount {
    protected int id;
    protected String name;
    protected double realPrice;
    protected double currentPrice;
    protected int nbItems;

    public Product(int id, String name, double realPrice, double currentPrice, int nbItems) {
        this.id = id;
        this.name = name;
        this.realPrice = realPrice;
        this.nbItems = nbItems;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
