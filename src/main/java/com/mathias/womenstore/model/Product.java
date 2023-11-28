package com.mathias.womenstore.model;

public abstract class Product {
    private int id;
    private String name;
    private double price;
    private int nbItems;

    public Product(int id, String name, double price, int nbItems) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.nbItems = nbItems;
    }

    /*
    public double sellProduct(int amount) throws IllegalArgumentException {
        if (amount > this.nbItems) {
            throw new IllegalArgumentException("You're trying to sell more products than the available stock.");
        }
        this.setNbItems(nbItems - amount);
        return amount * this.price;
    }

    public double purchase(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("You're trying to buy less than 0 products.");
        }
        this.nbItems += amount;
        return amount * this.price;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
