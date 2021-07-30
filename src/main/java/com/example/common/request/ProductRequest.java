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
	private float discount;
	private int id_brand;
	private int id_category;
	private byte[] image;
}
