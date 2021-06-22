package com.example.common.request;

/**
 * @author Tran Minh Truyen
 */


public class ProductRequest {

	private String name;
	private Float price;
	private String type;
	private byte[] image;

	public ProductRequest(String name, Float price, String type, byte[] image) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.image = image;
	}

	public ProductRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
