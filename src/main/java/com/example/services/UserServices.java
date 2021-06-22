package com.example.services;

import com.example.common.model.User;
import com.example.common.request.LoginRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Tran Minh Truyen
 */
public interface UserServices {
	boolean createUser(UserRequest userRequest);
	CommonResponse getAllUser(int page, int size);
	CommonResponse getUserByKeyWord(int page, int size, String keyword);
	User Login(LoginRequest loginRequest);
	boolean updateUser(int id, UserRequest request);
	boolean deleteUser(int id);
	UserDetails loadUserByUsername(String account);
	UserDetails loadUserById(int id);
}
