package com.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Tran Minh Truyen
 */

@Document
@Data
@AllArgsConstructor
public class ListProduct {

	@Field(value = "productId")
	private int id;

	@Field(value = "productName")
	private String productName;

	@Field(value = "productPrice")
	private float productPrice;

	@Field(value = "Discount")
	private float discount;

	@Field(value = "productQuatity")
	private long productAmount;
}
