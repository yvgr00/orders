package com.orderprocessing.orders.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orderprocessing.orders.entities.Customer;
import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	private OrderRepository orderRepository;
	
	
	public OrderServiceImpl(OrderRepository theOrderRepository) {
		
		this.orderRepository = theOrderRepository;
		
	}

	@Override
	public Order findById(Long theId) {
		// TODO Auto-generated method stub
		
		Optional<Order> result = orderRepository.findById(theId);
		
		Order order = null;
		if(result.isPresent()) {
			order = result.get();
		}else {
			throw new RuntimeException("Did not find order - "+theId);
		}
		return order;
	}

	@Override
	public void save(Order order) {
		// TODO Auto-generated method stub
		Customer cust = new Customer();
		
		

	}

	@Override
	public void deleteById(Long theId) {
		// TODO Auto-generated method stub
		
		orderRepository.deleteById(theId);

	}

}
