package com.example.common.response;

import com.example.common.model.Address;
import com.example.common.model.Role;
import com.example.common.request.UserRequest;

import java.util.Date;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */

public class UserResponse extends UserRequest {
	public UserResponse(String firstName, String lastName, Date birthDay, Address address, String citizenID, byte[] image, List<Role> role, boolean isActive) {
		super(firstName, lastName, birthDay, address, citizenID, image, role, isActive);
	}

	public UserResponse() {
	}
}
