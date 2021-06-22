package com.example.common.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@Document(collection = "Order")
public class Order {

	@Field(value = "id")
	private int id;

	@Field(value = "customerId")
	private int customerId;

	@Field(value = "employeeId")
	private int employeeId;

	@Field(value = "createDate")
	private Date createDate;

	@Field(value = "Cart")
	private Cart cart;

	@Field(value = "totalPrice")
	private float totalPrice;

	@Field(value = "status")
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
