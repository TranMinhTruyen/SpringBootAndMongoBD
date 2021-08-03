package com.example.services;

import com.example.common.request.CartRequest;
import com.example.common.request.OrderRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.OrderResponse;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public interface OrderServices {
	OrderResponse createOrder(int customerId);
	CommonResponse getOrderByCustomerId(int page, int size, int id);
	boolean updateOrder(int id, OrderRequest orderRequest);
	boolean deleteOrder(int id, int customerId);
}
