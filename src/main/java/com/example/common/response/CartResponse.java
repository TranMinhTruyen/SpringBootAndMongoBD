package com.example.common.response;

import com.example.common.model.ListProduct;
import com.example.common.request.CartRequest;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public class CartResponse extends CartRequest {

	private float totalPrice;

	public CartResponse(int id, List<ListProduct> productList, float totalPrice) {
		super(id, productList);
		this.totalPrice = totalPrice;
	}

	public CartResponse() {
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
