package com.example.common.response;

import com.example.common.request.ProductRequest;

/**
 * @author Tran Minh Truyen
 */
public class ProductResponse extends ProductRequest {
	private int id;

	public ProductResponse(String name, Float price, String type, byte[] image, int id) {
		super(name, price, type, image);
		this.id = id;
	}

	public ProductResponse() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
