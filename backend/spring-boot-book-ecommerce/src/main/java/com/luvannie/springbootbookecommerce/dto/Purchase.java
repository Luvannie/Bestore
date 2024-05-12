package com.luvannie.springbootbookecommerce.dto;

import com.luvannie.springbootbookecommerce.entity.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Order order;

    private Set<OrderItem> orderItems;

    private User user;


}
