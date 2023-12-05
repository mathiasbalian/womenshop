package com.mathias.womenstore.model;

public class Accessory extends Product {

    public Accessory(int id, String name, double realPrice, double currentPrice, int nbItems) {
        super(id, name, realPrice, currentPrice, nbItems);
    }

    @Override
    public void applyDiscount(double percentage) {
        setCurrentPrice(getRealPrice() * percentage);
    }
}
