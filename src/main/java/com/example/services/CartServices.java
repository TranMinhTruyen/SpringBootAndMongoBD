package com.example.services;

import com.example.common.model.Cart;
import com.example.common.request.CartRequest;
import com.example.common.response.CartResponse;
import com.example.repository.mongo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Tran Minh Truyen
 */
@Service
public class CartServices {

	@Autowired
	private CartRepository cartRepository;

	public boolean createCart(CartRequest cartRequest){
		if (cartRequest != null && !cartRepository.existsById(cartRequest.getId())){
			Cart newCart = new Cart();
			newCart.setId(cartRequest.getId());
			newCart.setProductList(cartRequest.getProductList());
			newCart.setTotalPrice(cartRequest.getTotalPrice());
			cartRepository.save(newCart);
			return true;
		}
		else return false;
	}

	public CartResponse getCartById(int id){
		Optional<Cart> result = cartRepository.findById(id);
		if (result.isPresent()){
			CartResponse cartResponse = new CartResponse();
			cartResponse.setId(result.get().getId());
			cartResponse.setProductList(result.get().getProductList());
			cartResponse.setTotalPrice(result.get().getTotalPrice());
			return cartResponse;
		}
		else return null;
	}

	public boolean updateProductList(CartRequest cartRequest){
		Optional<Cart> result = cartRepository.findById(cartRequest.getId());
		if (result.isPresent()){
			Cart update = result.get();
			update.setProductList(cartRequest.getProductList());
			cartRepository.save(update);
			return true;
		}
		else return false;
	}

	public boolean deleteCart(int id){
		if (cartRepository.existsById(id)){
			cartRepository.deleteById(id);
			return true;
		}
		else return false;
	}
}
