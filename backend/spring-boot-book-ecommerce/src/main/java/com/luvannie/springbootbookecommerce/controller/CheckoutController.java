package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.component.SecurityUtils;
import com.luvannie.springbootbookecommerce.dto.PaymentInfoDTO;
import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.responses.purchase.PurchaseResponse;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.service.checkout.ICheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private ICheckoutService checkoutService;
    private SecurityUtils securityUtils;

    public CheckoutController(ICheckoutService checkoutService, SecurityUtils securityUtils) {
        this.checkoutService = checkoutService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody PurchaseDTO purchaseDTO) throws DataNotFoundException {
        User loginUser = securityUtils.getLoggedInUser();
        if(purchaseDTO.getUserId() == null) {
            purchaseDTO.setUserId(loginUser.getId());
        }
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchaseDTO);
        return purchaseResponse;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoDTO paymentInfoDTO) throws StripeException {
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfoDTO);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);

    }
    }


