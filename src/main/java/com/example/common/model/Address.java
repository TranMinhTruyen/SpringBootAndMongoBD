package com.example.common.model;

public class Address {

    private String andress;
    private String distric;
    private String city;

    public String getAddress() {
        return andress;
    }

    public void setAddress(String andress) {
        this.andress = andress;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "Address='" + andress + '\'' +
                ", Distric='" + distric + '\'' +
                ", City='" + city + '\'' +
                '}';
    }
}
