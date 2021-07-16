package com.example.common.response;

import com.example.common.model.Cart;
import com.example.common.request.OrderRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Tran Minh Truyen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResponse extends OrderRequest {
	private int id;
	private float totalPrice;
}
