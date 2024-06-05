package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.dto.PaymentInfoDTO;
import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.responses.purchase.PurchaseResponse;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface ICheckoutService {

    PurchaseResponse placeOrder(PurchaseDTO purchaseDTO) throws DataNotFoundException;
    PaymentIntent createPaymentIntent(PaymentInfoDTO paymentInfoDTO) throws StripeException;
}
