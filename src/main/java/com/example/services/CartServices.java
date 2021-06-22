package com.example.services;

import com.example.common.request.CartRequest;
import com.example.common.response.CartResponse;
import org.springframework.stereotype.Service;

/**
 * @author Tran Minh Truyen
 */
public interface CartServices {
	boolean createCart(CartRequest cartRequest);
	CartResponse getCartById(int id);
	boolean updateProductList(CartRequest cartRequest);
	boolean deleteCart(int id);
}
