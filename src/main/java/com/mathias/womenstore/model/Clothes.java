package com.mathias.womenstore.model;

public class Clothes extends Product {
    private int size;

    public Clothes(int id, String name, double realPrice, double currentPrice, int nbItems, int size) {
        super(id, name, realPrice, currentPrice, nbItems);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void applyDiscount(double percentage) {
        setCurrentPrice(getRealPrice() * percentage);
    }
}
