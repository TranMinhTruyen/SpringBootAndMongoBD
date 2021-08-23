package com.core.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Tran Minh Truyen
 */

@Document
@Data
public class Address {

    @Field(value = "andress")
    private String andress;

    @Field(value = "distric")
    private String distric;

    @Field(value = "city")
    private String city;

    @Override
    public String toString() {
        return andress + ", " + distric + ", " + city;
    }
}
