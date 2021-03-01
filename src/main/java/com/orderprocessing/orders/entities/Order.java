package com.orderprocessing.orders.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
	
	
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="order_id")
	private Long orderId;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="customer_id")
    private Customer customer;
    
    @Column(name="order_status")
    private String orderStatus;
    
    @Column(name="order_sub_total")
    private Double orderSubTotal;
    
    @Column(name="order_total_amount")
    private Double orderTotalAmount;
    
    @Column(name="order_tax")
    private Double orderTax;
    
//    @OneToOne(cascade= {
//    		CascadeType.DETACH,
//    		CascadeType.MERGE,
//    		CascadeType.PERSIST,
//    		CascadeType.REFRESH,
//    		})
//    @JoinColumn(name="order_shipping_method")
//    private ShippingMethod orderShippingMethod;
    @Column(name="order_shipping_method")
    private String orderShippingMethod;
    
//    @Column(name="order_shipping_charges")
//    private Double orderShippingCharges;
    
    @OneToMany( 
    		fetch = FetchType.LAZY, 
    		cascade=CascadeType.ALL,
    		mappedBy="order"
    		)
    private List<Transaction> transactions;
    
    
    @OneToMany( 
    		fetch = FetchType.LAZY, 
    		cascade=CascadeType.ALL,
    		mappedBy="order"
    		)
    private List<OrderLineItems> orderLineItems;
    
    @Column(name="shipping_address_line1")
    private String shippingAddressLine1;
    
    @Column(name="shipping_address_line2")
    private String shippingAddressLine2;
    
    @Column(name="shipping_city")
    private String shippingCity;
    
    @Column(name="shipping_state")
    private String shippingState;
    
    @Column(name="shipping_zipcode")
    private String shipping_zipcode;
    
    
    @Basic(optional=false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_on")
    private Date createdOn;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_on")
    private Date updatedOn;
    
    
}
