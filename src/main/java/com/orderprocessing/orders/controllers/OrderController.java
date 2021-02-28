package com.orderprocessing.orders.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orderprocessing.orders.entities.Customer;
import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.entities.ShippingMethod;
import com.orderprocessing.orders.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	    @Autowired
	    private OrderService orderService;
	    	
	    public OrderController(OrderService theOrderService) {
			
	    	this.orderService = theOrderService;
		}


	    @GetMapping("/order/{id}")
	    public Order getOrderById(@PathVariable("id") final int id) {
	        return null;
	        
	        
	    }

	    @DeleteMapping("/order/cancel/{id}")
	    public void delOrderById(@PathVariable("id") final int id) {
	        System.out.println("nbgg");;
	    }

	    @PostMapping("/order")
	    public void createOrder(@RequestBody Order theOrder) {
	    	
	       
	        orderService.save(theOrder);
	        
	        
	        
	        
	        
	    }
	
}
