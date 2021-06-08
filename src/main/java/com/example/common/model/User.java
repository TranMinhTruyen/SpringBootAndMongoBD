package com.example.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.Date;

@Document(collection = "User")
public class User {
    @Id
    private int id;

    @Field(value = "First_Name")
    private String firstName;

    @Field(value = "Last_Name")
    private String lastName;

    @Field(value = "Birth_Day")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Address getAndress() {
        return address;
    }

    public void setAndress(Address address) {
        this.address = address;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", FirstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", BirthDay=" + birthDay +
                ", Address=" + address +
                ", CitizenId='" + citizenId + '\'' +
                ", Role='" + role + '\'' +
                ", Image=" + Arrays.toString(image) +
                ", IsActive=" + isActive +
                '}';
    }
}
