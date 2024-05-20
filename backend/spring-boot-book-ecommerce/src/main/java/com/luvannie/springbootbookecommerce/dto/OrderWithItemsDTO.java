package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithItemsDTO {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("order_date")
    private Date orderDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("order_items")
    private List<OrderItemDTO> orderItems;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("unit_price")
    private Double unitPrice;
}