package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.dto.PurchaseResponse;

public interface ICheckoutService {

    PurchaseResponse placeOrder(PurchaseDTO purchaseDTO);
}
