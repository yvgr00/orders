package com.orderprocessing.orders.services;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.ResponseDTO;

public interface OrderService {
	
	public RequestDTO findById(Long theId);
	
	public ResponseDTO saveOrder(RequestDTO order);
	
	public void deleteById(Long theId);

}
