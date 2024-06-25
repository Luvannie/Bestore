package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.responses.purchase.PurchaseResponse;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;

public interface ICheckoutService {

    PurchaseResponse placeOrder(PurchaseDTO purchaseDTO) throws DataNotFoundException;

}
