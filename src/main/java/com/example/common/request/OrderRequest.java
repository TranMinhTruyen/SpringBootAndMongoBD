package com.example.common.request;

import com.example.common.model.Cart;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author Tran Minh Truyen
 */
public class OrderRequest {

	private int customerId;
	private int employeeId;
	private Date createDate;
	private Cart cart;
	private String status;

	public OrderRequest() {
	}

	public OrderRequest(int customerId, int employeeId, Date createDate, Cart cart, String status) {
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.createDate = createDate;
		this.cart = cart;
		this.status = status;
	}


	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
