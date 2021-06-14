package com.example.repository.mongo;

import com.example.common.model.Cart;
import com.example.common.model.ListProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Tran Minh Truyen
 */
public interface CartRepository extends MongoRepository<Cart, Integer> {
	Cart findCartById(int id);
}
