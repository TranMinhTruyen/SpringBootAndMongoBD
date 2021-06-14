package com.example.common.response;

import com.example.common.model.ListProduct;
import com.example.common.request.CartRequest;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public class CartResponse extends CartRequest {
	public CartResponse(int userId, List<ListProduct> productList, long totalPrice) {
		super(userId, productList, totalPrice);
	}

	public CartResponse() {
	}
}
