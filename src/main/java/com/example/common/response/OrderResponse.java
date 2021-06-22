package com.example.common.response;

import com.example.common.model.Cart;
import com.example.common.request.OrderRequest;

import java.util.Date;

/**
 * @author Tran Minh Truyen
 */
public class OrderResponse extends OrderRequest {

	private int id;
	private float totalPrice;

	public OrderResponse() {
	}

	public OrderResponse(int id, int customerId, int employeeId, Date createDate, Cart cart, float totalPrice, String status) {
		super(customerId, employeeId, createDate, cart, status);
		this.id = id;
		this.totalPrice = totalPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
