package com.mathias.womenstore.model;

public class Shoe extends Product {

    private int shoeSize;

    public Shoe(int id, String name, double price, int nbItems, int shoeSize) {
        super(id, name, price, nbItems);
        this.shoeSize = shoeSize;
    }

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        this.shoeSize = shoeSize;
    }
}
