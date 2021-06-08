package com.example.common.model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Andress {

    @Field(value = "Andress")
    private String andress;

    @Field(value = "Distric")
    private String distric;

    @Field(value = "City")
    private String city;

    public String getAndress() {
        return andress;
    }

    public void setAndress(String andress) {
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
        return "Andress{" +
                "Andress='" + andress + '\'' +
                ", Distric='" + distric + '\'' +
                ", City='" + city + '\'' +
                '}';
    }
}
