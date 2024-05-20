package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("unit_price")
    private Double unitPrice;
}


