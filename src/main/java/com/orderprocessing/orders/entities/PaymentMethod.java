package com.orderprocessing.orders.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="payment_method")
public class PaymentMethod {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="payment_method_id")
	private final int paymentMethodId = 0;
	
	@Column(name="payment_method")
	private String paymentMethod;
	
	@Column(name="billing_address_line1")
	private String billingAddressLine1;
	
	@Column(name="billing_address_line2")
	private String billingAddressLine2;
	
	@Column(name="billing_city")
	private String billingCity;
	
	
    @Column(name="billing_state")
    private String billingState;
    
    
    @Column(name="billing_zipcode")
    private String billingZipcode;
    
    @OneToMany( 
    		fetch = FetchType.LAZY, 
    		cascade=CascadeType.ALL,
    		mappedBy="payment_method"
    		)
    private List<Transaction> transactions;
    
    
	
	

}