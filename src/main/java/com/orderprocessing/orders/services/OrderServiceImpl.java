package com.orderprocessing.orders.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.orders.controllers.OrderController;
import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.ResponseDTO;
import com.orderprocessing.orders.entities.Customer;
import com.orderprocessing.orders.entities.Item;
import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.entities.OrderLineItems;
import com.orderprocessing.orders.entities.PaymentMethod;
import com.orderprocessing.orders.entities.Transaction;
import com.orderprocessing.orders.repository.CustomerRepository;
import com.orderprocessing.orders.repository.ItemRepository;
import com.orderprocessing.orders.repository.OrderLineItemsRepository;
import com.orderprocessing.orders.repository.OrderRepository;
import com.orderprocessing.orders.repository.PaymentMethodRepository;
import com.orderprocessing.orders.repository.TransactionRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private OrderLineItemsRepository orderLineItemsRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	private  static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Override
	public Order findOrderById(String theId) {
		// TODO Auto-generated method stub
		
		Optional<Order> result = orderRepository.findById(theId);
//		ResponseDTO response = new ResponseDTO();
		
		Order order = null;
		if(result.isPresent()) {
			order = result.get();
		}else {
			throw new RuntimeException("Did not find order - "+theId);
		}
		
//		logger.info("ddff     "+order.getOrderLineItems());
//		List<OrderLineItems> orderLineItems = orderLineItemsRepo.findByOrder(order);
//		List<Transaction> transactions = transactionRepo.findByOrder(order);
//		
//		
//		order.setOrderLineItems(orderLineItems);
//		order.setTransactions(transactions);
		
		
//		response.setOrder(order);
//		response.setMessage("found order_id");
		return order;
	}

	@Override
	public ResponseDTO saveOrder(RequestDTO theOrder) {
		//check if order amount is correct
		ResponseDTO result = new ResponseDTO();
		double orderTotal = 0;
		for(int j=0; j<theOrder.getOrderLineItems().size(); j++) {
			OrderLineItems o = theOrder.getOrderLineItems().get(j);
			Optional<Item> io = itemRepository.findById(o.getOrderItemId());
			Item i = null;
			if(io.isPresent()) {
				i = io.get();
			}else {
				//throw new RuntimeException("Did not find item - " + o.getOrderItemId());
			    result.setOrder(null);
			    result.setMessage("Did not find item - " + o.getOrderItemId());
				return result;
			}
			orderTotal = orderTotal + i.getItemPrice()*o.getQuantity();
		}
		if(theOrder.getOrderSubTotal() != orderTotal) {
			//throw new RuntimeException("Did not match order sub total");
			result.setOrder(null);
		    result.setMessage("Did not match order sub total");
		    return result;
		}
		// add tax
		orderTotal = orderTotal + orderTotal*0.0625;
		if(theOrder.getOrderTotalAmount() != orderTotal) {
			//throw new RuntimeException("Did not match order total");
			result.setOrder(null);
		    result.setMessage("Did not match order total");
		    return result;
		}
		
		// TODO Auto-generated method stub
		Customer cust = new Customer();
		cust.setFirstName(theOrder.getCustomer().getFirstName());
		cust.setLastName(theOrder.getCustomer().getLastName());
		cust.setMiddleName(theOrder.getCustomer().getMiddleName());
        cust.setEmail(theOrder.getCustomer().getEmail());
		
       
       
      
        
       List<PaymentMethod> payments = theOrder.getPaymentMethod();
       
//       ObjectMapper objectMapper = new ObjectMapper();
//       String toSHA = "";
//       try {
//    	   toSHA = objectMapper.writeValueAsString(theOrder) + (new Date()).toString();
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//       
//       //Convert string to bytes
//       byte bytes[] = toSHA.getBytes();
//       
//       Checksum checksum = new Adler32();
//       checksum.update(bytes,0,bytes.length);
//       
//       long orderId = checksum.getValue();
//       
//       logger.info("ddffff"+orderId);
       
//       List<Transaction> transactions = new ArrayList<Transaction>();
//       for(int i1=0; i1<payments.size(); i1++) {
//    	   PaymentMethod pIn = payments.get(i1);
//    	   Optional<PaymentMethod> po = paymentMethodRepository.findById(payments.get(i1).getPaymentMethodId());
//    	   if(!po.isPresent()) {
//				PaymentMethod pwrite = new PaymentMethod();
//				pwrite.setPaymentMethod(pIn.getPaymentMethod());
//				logger.info("Payment_method:"+pIn.getPaymentMethod());
//				pwrite.setPaymentMethodId(pIn.getPaymentMethodId());
//				pwrite.setBillingAddressLine1(pIn.getBillingAddressLine1());
//				pwrite.setBillingAddressLine2(pIn.getBillingAddressLine2());
//				pwrite.setBillingCity(pIn.getBillingCity());
//				pwrite.setBillingState(pIn.getBillingState());
//				pwrite.setBillingZipcode(pIn.getBillingZipcode());
//				paymentMethodRepository.save(pwrite);
//			}
//    	   Transaction t = new Transaction();
//    	   t.setPaymentMethod(pIn);
//    	   t.setTransactionAmount(theOrder.getPaymentAmount().get(i1));
//    	   transactions.add(t);
//       }
       
//       "shipping_address_line1":"373 crescent ave",
//       "shipping_address_line2":null,
//       "shipping_city":"Boston",
//       "shipping_state":"MA",
//       "shipping_zipcode":"02150",
//       "refund_status":"false"
       
       Order order = new Order();
//       order.setOrderId(orderId);
       order.setCustomer(cust);
//       order.setTransactions(transactions);
       order.setShippingAddressLine1(theOrder.getShipping_address_line1());
       order.setShippingAddressLine2(theOrder.getShipping_address_line2());
       order.setOrderShippingMethod(theOrder.getShippingMethod());
       order.setShippingCity(theOrder.getShipping_city());
       order.setShippingState(theOrder.getShipping_state());
       order.setShipping_zipcode(theOrder.getShipping_zipcode());
       order.setOrderSubTotal(theOrder.getOrderSubTotal());
       order.setOrderTotalAmount(theOrder.getOrderTotalAmount());
       order.setOrderStatus("Confirmed");
       order.setOrderTax(theOrder.getTax());
       Date orderedDate = new Date();
       order.setCreatedOn(orderedDate);
       order.setUpdatedOn(orderedDate);
       customerRepo.save(cust);
       
       logger.info("dgggggggg    "+order.getCustomer().getEmail());
//       List<OrderLineItems> olt = new ArrayList<OrderLineItems>();
       for(int j=0; j<theOrder.getOrderLineItems().size(); j++) {
			OrderLineItems o = theOrder.getOrderLineItems().get(j);
			Optional<Item> io = itemRepository.findById(o.getOrderItemId());
			Item i = null;
			if(io.isPresent()) {
				i = io.get();
				OrderLineItems orderLineItems = new OrderLineItems();
				orderLineItems.setItem(i);
				orderLineItems.setQuantity(o.getQuantity());
				orderLineItems.setOrder(order);
				order.addOrderLineItems(orderLineItems);
//				olt.add(orderLineItems);
//				orderLineItemsRepo.save(orderLineItems);
			
		} 
      }
       
       
//       List<Transaction> transactions = new ArrayList<Transaction>();
       for(int i1=0; i1<payments.size(); i1++) {
    	   PaymentMethod pIn = payments.get(i1);
    	   Optional<PaymentMethod> po = paymentMethodRepository.findById(payments.get(i1).getPaymentMethodId());
    	   if(!po.isPresent()) {
//				"payment_method":"visa",
//	            "card_number": 6011190523805318,
//	            "billing_address_line1":"373 crescent ave",
//	            "billing_address_line2":null,
//	            "billing_city":"Boston",
//	            "billing_state":"MA",
//	            "billing_zipcode":"02150",
				PaymentMethod pwrite = new PaymentMethod();
				pwrite.setPaymentMethod(pIn.getPaymentMethod());
				logger.info("Payment_method:"+pIn.getPaymentMethod());
				pwrite.setPaymentMethodId(pIn.getPaymentMethodId());
				pwrite.setBillingAddressLine1(pIn.getBillingAddressLine1());
				pwrite.setBillingAddressLine2(pIn.getBillingAddressLine2());
				pwrite.setBillingCity(pIn.getBillingCity());
				pwrite.setBillingState(pIn.getBillingState());
				pwrite.setBillingZipcode(pIn.getBillingZipcode());
//				paymentMethodRepository.save(pwrite);
			}
    	   Transaction t = new Transaction();
    	   t.setPaymentMethod(pIn);
    	   t.setTransactionAmount(theOrder.getPaymentAmount().get(i1));
    	   t.setOrder(order);
    	   t.setTransactionStatus("complete");
    	   order.addTransactions(t);
//    	   transactions.add(t);
//    	   transactionRepo.save(t);
       }
       orderRepository.save(order);
//       order.setOrderLineItems(olt);
//       order.setTransactions(transactions);
       
       
       result.setOrder(order);
       result.setMessage("success");
       
       return result;
	  
	}
       
	

	@Override
	public void deleteById(String theId) {
		// TODO Auto-generated method stub
		
		orderRepository.deleteById(theId);

	}

}
