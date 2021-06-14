package com.example.common.model;

/**
 * @author Tran Minh Truyen
 */

public class Role {
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role{" +
				"role='" + role + '\'' +
				'}';
	}
}
