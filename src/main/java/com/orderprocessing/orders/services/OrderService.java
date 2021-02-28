package com.orderprocessing.orders.services;

import com.orderprocessing.orders.entities.Order;

public interface OrderService {
	
	public Order findById(Long theId);
	
	public void save(Order order);
	
	public void deleteById(Long theId);

}
