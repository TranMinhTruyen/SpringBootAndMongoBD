package com.example.common.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Tran Minh Truyen
 */

@Document
@Data
public class ListProduct {

	@Field(value = "productName")
	private String productName;

	@Field(value = "productPrice")
	private float productPrice;

	@Field(value = "productQuatity")
	private long productAmount;
}
