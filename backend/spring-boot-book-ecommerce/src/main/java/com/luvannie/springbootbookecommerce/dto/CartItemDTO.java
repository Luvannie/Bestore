package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {
    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("user_id")
    private Long userId;
}
