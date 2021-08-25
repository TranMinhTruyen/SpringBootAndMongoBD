package com.example.userservices.services;

import com.core.model.User;
import com.core.request.LoginRequest;
import com.core.request.UserRequest;
import com.core.response.CommonResponse;
import com.core.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserServices {
    UserResponse createUser(UserRequest userRequest);
    CommonResponse getAllUser(int page, int size);
    CommonResponse getUserByKeyWord(int page, int size, String keyword);
    User Login(LoginRequest loginRequest);
    User resetPassword(String email);
    void sendEmailConfirmKey(String email, String confirmKey);
    boolean checkConfirmKey(String email, String confirmKey);
    UserResponse updateUser(int id, UserRequest request);
    boolean deleteUser(int id);
    boolean accountIsExists(String account, String email);
    UserDetails loadUserByUsername(String account);
    UserDetails loadUserById(int id);
}
