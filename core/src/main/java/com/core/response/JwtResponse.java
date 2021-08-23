package com.core.response;

import lombok.Data;

/**
 * @author Tran Minh Truyen
 */
@Data
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	public JwtResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
