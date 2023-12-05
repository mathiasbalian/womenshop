package com.mathias.womenstore.model;

public class Shoe extends Product {

    private int shoeSize;

    public Shoe(int id, String name, double realPrice, double currentPrice, int nbItems, int shoeSize) {
        super(id, name, realPrice, currentPrice, nbItems);
        this.shoeSize = shoeSize;
    }

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        this.shoeSize = shoeSize;
    }

    @Override
    public void applyDiscount(double percentage) {
        setCurrentPrice(getRealPrice() * percentage);
    }
}
