package com.example.common.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@Document(collection = "Cart")
public class Cart {

	@Id
	private int id;

	@Field(value = "productName")
	private List<ListProduct> productList;

	@Field(value = "totalPrice")
	private float totalPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ListProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<ListProduct> productList) {
		this.productList = productList;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Cart{" +
				"userId=" + id +
				", productList=" + productList +
				", totalPrice=" + totalPrice +
				'}';
	}
}
