package com.luvannie.springbootbookecommerce.responses.order;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.dto.CartItemDTO;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.OrderItem;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
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

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;


    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;

    public static OrderResponse fromOrder(Order order){

        Date date = order.getShippingDate();
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        List<OrderItem> orderItems1 = (List<OrderItem>) order.getOrderItems();
        List<OrderItemResponse> orderItemResponses = orderItems1.stream().map(OrderItemResponse::fromOrderItem).toList();
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getCustomer().getId())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getDateCreated())
                .status(order.getStatus())
                .orderItems(orderItemResponses)
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress().getSpecificAddress())
                .shippingDate(localDate)
                .paymentMethod(order.getPaymentMethod())
                .build();
    }
}
