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

	@Field(value = "listProduct")
	private List<ListProduct> listProduct;

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

	public List<ListProduct> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<ListProduct> listProduct) {
		this.listProduct = listProduct;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
