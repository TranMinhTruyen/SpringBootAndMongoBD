package com.core.request;

import com.core.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@Data
public class UserRequest {

    private String account;

    private String password;

    private String firstName;
    private String lastName;
    private Date birthDay;
    private Address address;
    private String citizenID;
    private String email;
    private byte[] image;
    private String role;
    private boolean isActive;
}
