package com.example.common.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Address {

    @Field(value = "andress")
    private String andress;

    @Field(value = "distric")
    private String distric;

    @Field(value = "city")
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
