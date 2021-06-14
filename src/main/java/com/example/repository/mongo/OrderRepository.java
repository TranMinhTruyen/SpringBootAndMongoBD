package com.example.repository.mongo;

import com.example.common.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Tran Minh Truyen
 */
public interface OrderRepository extends MongoRepository<Order, Integer> {
	Order findOrderByCustomerId(int customerId);
	Order findOrderById(int id);
}
