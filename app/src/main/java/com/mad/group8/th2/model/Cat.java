package com.mad.group8.th2.model;

public class Cat {
    private int id;
    private String name;
    private double price;
    private String description;
    private int imgId;

    // Constructor
    public Cat(int id, String name, double price, String description, int imgId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgId = imgId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}

