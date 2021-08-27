package com.example.cartservices.services;

import com.core.response.CartResponse;

public interface CartServices {
    boolean createCart(int customerId, int productId);
    CartResponse getCartById(int id);
    boolean updateProductAmount(int customerId, int productId, long amount);
    boolean deleteCart(int id);
    boolean addProductToCart(int customerId, int productId);
    boolean removeProductFromCart(int customerId, int productId);
    boolean isCartExists(int customerId);
}
