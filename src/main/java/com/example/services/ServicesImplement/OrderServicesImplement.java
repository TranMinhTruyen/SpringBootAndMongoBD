package com.example.services.ServicesImplement;

import com.example.common.model.AutoIncrement;
import com.example.common.model.Order;
import com.example.common.model.User;
import com.example.common.request.OrderRequest;
import com.example.common.response.CommonResponse;
import com.example.repository.mongo.OrderRepository;
import com.example.services.OrderServices;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Tran Minh Truyen
 */
@Service
public class OrderServicesImplement implements OrderServices {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public boolean createOrder(OrderRequest orderRequest) {
		List<User> last  = new AutoIncrement(orderRepository).getLastOfCollection();
		if (orderRequest != null) {
			Order newOrder = new Order();
			if (last != null)
				newOrder.setId(last.get(0).getId()+1);
			else newOrder.setId(1);
			newOrder.setCustomerId(orderRequest.getCustomerId());
			newOrder.setEmployeeId(orderRequest.getEmployeeId());
			newOrder.setCreateDate(orderRequest.getCreateDate());
			newOrder.setCart(orderRequest.getCart());
			newOrder.setStatus(orderRequest.getStatus());
			newOrder.setTotalPrice(orderRequest.getCart().getTotalPrice());
			orderRepository.save(newOrder);
			return true;
		}
		else return false;
	}

	@Override
	public CommonResponse getOrderByCustomerId(int page, int size, int id) {
		List result = orderRepository.findOrderByCustomerId(id);
		if (result != null){
			return new CommonResponse().getCommonResponse(page, size, result);
		}
		else return null;
	}

	@Override
	public boolean updateOrder(int id, OrderRequest orderRequest) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isPresent()){
			Order update = order.get();
			update.setCustomerId(orderRequest.getCustomerId());
			update.setEmployeeId(orderRequest.getEmployeeId());
			update.setCreateDate(orderRequest.getCreateDate());
			update.setStatus(orderRequest.getStatus());
			orderRepository.save(update);
			return true;
		}
		else return false;
	}


	@Override
	public boolean updateCartInOrder(int id, OrderRequest orderRequest) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isPresent()){
			Order update = order.get();
			update.setCart(orderRequest.getCart());
			orderRepository.save(update);
			return true;
		}
		else return false;
	}


	@Override
	public boolean deleteOrder(int id) {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()){
			orderRepository.deleteById(id);
			return true;
		}
		else return false;
	}
}
