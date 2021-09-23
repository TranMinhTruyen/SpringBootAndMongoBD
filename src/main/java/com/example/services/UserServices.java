package com.example.services;

import com.example.common.model.User;
import com.example.common.request.LoginRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Tran Minh Truyen
 */
public interface UserServices {
	UserResponse createUser(UserRequest userRequest);
	CommonResponse getAllUser(int page, int size);
	CommonResponse getUserByKeyWord(int page, int size, String keyword);
	User Login(LoginRequest loginRequest);
	User resetPassword(String email);
	UserResponse getProfileUser(int id);
	String sendEmailConfirmKey(String email, String confirmKey);
	boolean checkConfirmKey(String email, String confirmKey);
	UserResponse updateUser(int id, UserRequest request);
	boolean deleteUser(int id);
	boolean accountIsExists(String account, String email);
	UserDetails loadUserByUsername(String account);
	UserDetails loadUserById(int id);
}
