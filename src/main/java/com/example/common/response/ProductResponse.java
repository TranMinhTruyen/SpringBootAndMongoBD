package com.example.common.response;

import com.example.common.request.ProductRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tran Minh Truyen
 */
@Data
public class ProductResponse {
	private int id;
	private String name;
	private Float price;
	private String type;
	private float discount;
	private String brand;
	private String category;
	private byte[] image;
}
