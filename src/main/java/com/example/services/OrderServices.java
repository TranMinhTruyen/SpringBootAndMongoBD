package com.example.services;

import com.example.common.request.CartRequest;
import com.example.common.request.OrderRequest;
import com.example.common.response.CommonResponse;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public interface OrderServices {
	boolean createOrder(OrderRequest orderRequest);
	CommonResponse getOrderByCustomerId(int page, int size, int id);
	boolean updateOrder(int id, OrderRequest orderRequest);
	boolean deleteOrder(int id);
}
