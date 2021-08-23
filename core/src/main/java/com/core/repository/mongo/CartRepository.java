package com.core.repository.mongo;

import com.core.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Tran Minh Truyen
 */
public interface CartRepository extends MongoRepository<Cart, Integer> {
}
