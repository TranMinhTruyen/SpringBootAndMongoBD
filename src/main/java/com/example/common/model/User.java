package com.example.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */

@Document(collection = "User")
@Data
public class User {

    @Field(value = "id")
    private int id;

    @Field(value = "Account")
    private String account;

    @Field(value = "Password")
    private String password;

    @Field(value = "FirstName")
    private String firstName;

    @Field(value = "LastName")
    private String lastName;

    @Field(value = "BirthDay")
    private Date birthDay;

    @Field(value = "Address")
    private Address address;

    @Field(value = "CitizenID")
    private String citizenId;

    @Field(value = "Role")
    private String role;

    @Field(value = "Image")
    private byte[] image;

    @Field(value = "IsActive")
    private boolean isActive;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", address=" + address +
                ", citizenId='" + citizenId + '\'' +
                ", role=" + role +
                ", image=" + Arrays.toString(image) +
                ", isActive=" + isActive +
                '}';
    }
}
