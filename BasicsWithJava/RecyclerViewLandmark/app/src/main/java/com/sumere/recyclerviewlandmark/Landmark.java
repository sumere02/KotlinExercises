package com.sumere.recyclerviewlandmark;

import java.io.Serializable;

public class Landmark implements Serializable {
    private String name;
    private String country;
    private int imageView;

    public Landmark(String name,String country,int imageView){
        this.name = name;
        this.country = country;
        this.imageView = imageView;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
