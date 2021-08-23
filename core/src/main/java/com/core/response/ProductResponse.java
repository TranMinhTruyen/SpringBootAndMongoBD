package com.core.response;

import lombok.Data;

/**
 * @author Tran Minh Truyen
 */
@Data
public class ProductResponse {
	private int id;
	private String name;
	private float price;
	private String type;
	private float discount;
	private float unitInStock;
	private String brand;
	private String category;
	private byte[] image;
}
