package com.example.common.request;

import lombok.Data;

/**
 * @author Tran Minh Truyen
 */

@Data
public class ProductRequest {

	private String name;
	private Float price;
	private String type;
	private byte[] image;

}
