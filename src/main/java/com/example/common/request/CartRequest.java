package com.example.common.request;

import com.example.common.model.ListProduct;

import javax.persistence.Id;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public class CartRequest {

	private int id;
	private List<ListProduct> productList;
	private long totalPrice;

	public CartRequest(int id, List<ListProduct> productList, long totalPrice) {
		this.id = id;
		this.productList = productList;
		this.totalPrice = totalPrice;
	}

	public CartRequest() {
	}

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

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
}
