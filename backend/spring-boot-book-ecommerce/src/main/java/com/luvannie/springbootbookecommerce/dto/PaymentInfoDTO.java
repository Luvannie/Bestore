package com.luvannie.springbootbookecommerce.dto;

import lombok.Data;

@Data
public class PaymentInfoDTO {
    private int amount;
    private String currency;
}
