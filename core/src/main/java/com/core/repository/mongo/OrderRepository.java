package com.core.repository.mongo;

import com.core.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public interface OrderRepository extends MongoRepository<Order, Integer>{
	List findOrderByCustomerId(int customerId);
	Order findOrderById(int id);
	Order findOrderByIdAndCustomerId(int id, int customerId);
}
