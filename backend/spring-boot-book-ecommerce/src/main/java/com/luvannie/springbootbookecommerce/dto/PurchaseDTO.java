package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
public class PurchaseDTO {

    private Customer customer;

    private Address shippingAddress;

    private Order order;

    private Set<OrderItem> orderItems;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("shipping_method")
    private String shippingMethod;


    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("coupon_code")
    private String couponCode;


}
