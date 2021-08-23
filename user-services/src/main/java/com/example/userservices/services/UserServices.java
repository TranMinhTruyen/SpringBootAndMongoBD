package com.example.userservices.services;

import com.core.model.User;
import com.core.request.LoginRequest;
import com.core.request.UserRequest;
import com.core.response.CommonResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserServices {
    boolean createUser(UserRequest userRequest);
    CommonResponse getAllUser(int page, int size);
    CommonResponse getUserByKeyWord(int page, int size, String keyword);
    User Login(LoginRequest loginRequest);
    boolean updateUser(int id, UserRequest request);
    boolean deleteUser(int id);
    boolean accountIsExists(String account);
    UserDetails loadUserByUsername(String account);
    UserDetails loadUserById(int id);
}
