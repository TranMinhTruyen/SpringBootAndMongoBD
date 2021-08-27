package com.core.model;

import com.google.common.hash.Hashing;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Tran Minh Truyen
 */

@Document(collection = "User")
@Data
public class User {

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

    @Field(value = "Mail")
    private String email;

    @Field(value = "Role")
    private String role;

    @Field(value = "Image")
    private byte[] image;

    @Field(value = "IsActive")
    private boolean isActive;

    public void setPassword(String password) {
        this.password = Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public void setAccount(String account) {
        this.account = Hashing.sha512().hashString(account, StandardCharsets.UTF_8).toString();
    }

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
