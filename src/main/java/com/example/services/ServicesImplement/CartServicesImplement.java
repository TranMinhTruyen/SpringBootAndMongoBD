package com.example.services.ServicesImplement;

import com.example.common.model.Cart;
import com.example.common.model.ListProduct;
import com.example.common.request.CartRequest;
import com.example.common.response.CartResponse;
import com.example.repository.mongo.CartRepository;
import com.example.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Tran Minh Truyen
 */
@Service
public class CartServicesImplement implements CartServices {

	@Autowired
	private CartRepository cartRepository;

	@Override
	public boolean createCart(CartRequest cartRequest){
		if (cartRequest != null && !cartRepository.existsById(cartRequest.getId())){
			Cart newCart = new Cart();
			float totalPrice = 0;
			newCart.setId(cartRequest.getId());
			newCart.setProductList(cartRequest.getProductList());
			for (ListProduct c: cartRequest.getProductList()){
				totalPrice += c.getProductPrice() * c.getProductAmount();
			}
			newCart.setTotalPrice(totalPrice);
			cartRepository.save(newCart);
			return true;
		}
		else return false;
	}

	@Override
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

	@Override
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

	@Override
	public boolean deleteCart(int id){
		if (cartRepository.existsById(id)){
			cartRepository.deleteById(id);
			return true;
		}
		else return false;
	}
}
