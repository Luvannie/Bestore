package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.dto.Purchase;
import com.luvannie.springbootbookecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
