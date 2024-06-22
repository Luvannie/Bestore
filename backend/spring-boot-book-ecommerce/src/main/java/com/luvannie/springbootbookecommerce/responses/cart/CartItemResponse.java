package com.luvannie.springbootbookecommerce.responses.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.CartItem;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("book_id")
    private long bookId;

    @JsonProperty("unit_price")
    private double unitPrice;

    @JsonProperty("quantity")
    private int quantity;

    public static CartItemResponse fromCartItem(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .userId(cartItem.getUserId())
                .thumbnail(cartItem.getThumbnail())
                .bookId(cartItem.getBookId())
                .unitPrice(cartItem.getUnitPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
