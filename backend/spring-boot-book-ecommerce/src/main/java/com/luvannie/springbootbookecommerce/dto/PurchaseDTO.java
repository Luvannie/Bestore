package com.luvannie.springbootbookecommerce.dto;

import com.luvannie.springbootbookecommerce.entity.*;
import lombok.Data;

import java.util.Set;
@Data
public class PurchaseDTO {

    private Customer customer;

    private Address shippingAddress;

    private Order order;

    private Set<OrderItem> orderItems;

    private Long userId;

    private String couponCode;


}
