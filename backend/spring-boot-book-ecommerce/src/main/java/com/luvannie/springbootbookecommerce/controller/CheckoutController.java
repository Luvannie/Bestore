package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.dto.PurchaseResponse;
import com.luvannie.springbootbookecommerce.service.checkout.ICheckoutService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private ICheckoutService checkoutService;

    public CheckoutController(ICheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody PurchaseDTO purchaseDTO) {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchaseDTO);
        return purchaseResponse;
    }



    }


