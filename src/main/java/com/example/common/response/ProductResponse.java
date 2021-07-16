package com.example.common.response;

import com.example.common.request.ProductRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tran Minh Truyen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductResponse extends ProductRequest {
	private int id;
}
