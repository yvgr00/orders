package com.orderprocessing.orders.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.ResponseDTO;
import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.services.OrderService;

//import io.swagger.annotations.Tag;
//import springfox.documentation.service.MediaTypes;

//@Tag(name = "action", description = "Action API")
@RestController
@RequestMapping("/api")

public class OrderController {

	
	private  static final Logger logger = LoggerFactory.getLogger(OrderController.class);
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

	    @PostMapping(value = "/orderback", consumes = {MediaType.APPLICATION_JSON_VALUE}, 
	    		produces = {MediaType.APPLICATION_JSON_VALUE})
	    public @Valid RequestDTO createOrder(@Valid @RequestBody RequestDTO theOrder) {
	    	return theOrder;
	    }
	    
	    
	    @PostMapping(value = "/order", consumes = {MediaType.APPLICATION_JSON_VALUE}, 
	    		produces = {MediaType.APPLICATION_JSON_VALUE})
	    public ResponseEntity<ResponseDTO> createOrder2(@Valid @RequestBody RequestDTO theOrder) {
	    	logger.info("OrderController: createOrder2() endpoint is called for request: " + theOrder.toString());
	    	
	    	HttpStatus httpStatus = HttpStatus.OK;
	    	ResponseDTO responseDto = new ResponseDTO();
	    	//return theOrder;
	    	// Check total and paymentsAmount
	    	Double totalAmnt = theOrder.getOrderTotalAmount();
	    	List<Double> paymentAmnts = theOrder.getPaymentAmount();
	    	Double paidAmntTotal = paymentAmnts.stream().mapToDouble(Double::doubleValue).sum();
	    	
	    	if(round(totalAmnt,2) != round(paidAmntTotal,2)) {
	    		httpStatus = HttpStatus.BAD_REQUEST;
	    		logger.info("Payments not ok. Need "+totalAmnt+". Received "+paidAmntTotal);
	    		responseDto.setMessage("Payments not ok. Need "+totalAmnt+". Received "+paidAmntTotal);
	    		return new ResponseEntity<ResponseDTO>(responseDto, httpStatus);
	    	}
	    	
	    	
	    	httpStatus = HttpStatus.OK;
	    	responseDto = orderService.saveOrder(theOrder);
	    	
	    	
	    	if(responseDto.getOrder() != null) {
	    		logger.info("Successfully generated order confirmation id");
	    	} else {
	    		logger.error("Order confirmation response is null for the request" + theOrder.toString());
	    		httpStatus = HttpStatus.BAD_REQUEST;
	    	}
	    	logger.info(responseDto.getMessage());
	    	return new ResponseEntity<ResponseDTO>(responseDto, httpStatus);
	    }
	    
	    
	    @GetMapping("/")
	    public String getbh() {
	    	return "venu";
	    }
	    
	    public static double round(double value, int places) {
	        if (places < 0) throw new IllegalArgumentException();

	        BigDecimal bd = BigDecimal.valueOf(value);
	        bd = bd.setScale(places, RoundingMode.HALF_UP);
	        return bd.doubleValue();
	    }
	
}
