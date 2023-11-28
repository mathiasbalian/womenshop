package com.mathias.womenstore.model;

public class Clothes extends Product {
    private int size;

    public Clothes(int id, String name, double price, int nbItems, int size) {
        super(id, name, price, nbItems);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
