package com.hotel.service;
 import com.hotel.model.Order;
import com.stripe.exception.StripeException;
import com.hotel.Response.PaymentResponse;

public interface PaymentService {
      
	public PaymentResponse createPaymentLink(Order order ) throws StripeException ;
}
