package com.luvannie.springbootbookecommerce.responses.order;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.OrderItem;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("book_id")
    private long bookId;

    @JsonProperty("unit_price")
    private double unitPrice;

    @JsonProperty("quantity")
    private int quantity;

    public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .thumbnail(orderItem.getThumbnail())
                .bookId(orderItem.getBookId())
                .unitPrice(orderItem.getUnitPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
