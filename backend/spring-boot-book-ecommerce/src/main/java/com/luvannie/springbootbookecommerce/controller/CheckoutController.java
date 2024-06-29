package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.component.SecurityComponent;
import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.responses.purchase.PurchaseResponse;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.service.checkout.ICheckoutService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private ICheckoutService checkoutService;
    private SecurityComponent securityComponent;

    public CheckoutController(ICheckoutService checkoutService, SecurityComponent securityComponent) {
        this.checkoutService = checkoutService;
        this.securityComponent = securityComponent;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody PurchaseDTO purchaseDTO) throws DataNotFoundException {
        User loginUser = securityComponent.getLoggedInUser();
        if(purchaseDTO.getUserId() == null) {
            purchaseDTO.setUserId(loginUser.getId());
        }
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchaseDTO);
        return purchaseResponse;
    }
    }


