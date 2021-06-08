package com.example.common.request;

import com.example.common.model.Andress;

import java.util.Date;

public class UserRequest {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private Andress andress;
    private String citizenID;
    private byte[] image;
    private String role;
    private boolean isActive;

    public UserRequest() {
    }

    public UserRequest(int id, String firstName, String lastName, Date birthDay, Andress andress, String citizenID, byte[] image, String role, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.andress = andress;
        this.citizenID = citizenID;
        this.image = image;
        this.role = role;
        this.isActive = isActive;
    }

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

    public Andress getAndress() {
        return andress;
    }

    public void setAndress(Andress andress) {
        this.andress = andress;
    }

    public String getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
