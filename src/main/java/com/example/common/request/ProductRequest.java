package com.example.common.request;

import lombok.Data;

/**
 * @author Tran Minh Truyen
 */

@Data
public class ProductRequest {
	private String name;
	private float price;
	private String type;
	private float discount;
	private float unitInStock;
	private int id_brand;
	private int id_category;
	private byte[] image;
}
