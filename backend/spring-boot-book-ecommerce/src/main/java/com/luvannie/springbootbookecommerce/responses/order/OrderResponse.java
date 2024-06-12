package com.luvannie.springbootbookecommerce.responses.order;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.dto.CartItemDTO;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.OrderItem;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("customer_email")
    private String customerEmail;

    @JsonProperty("customer_phone")
    private String customerPhone;

    @JsonProperty("shipping_country")
    private String shippingCountry;

    @JsonProperty("shipping_city")
    private String shippingCity;

    @JsonProperty("shipping_district")
    private String shippingDistrict;

    @JsonProperty("date_created")
    private Date dateCreated;

    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;

    public static OrderResponse fromOrder(Order order){

        Date date = order.getShippingDate();
        LocalDate localDate = null;
        if (date != null) {
            localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        List<OrderItem> orderItems1 =  new ArrayList<>(order.getOrderItems());
        List<OrderItemResponse> orderItemResponses = orderItems1.stream().map(OrderItemResponse::fromOrderItem).toList();
        return OrderResponse.builder()
                .trackingNumber(order.getOrderTrackingNumber())
                .orderId(order.getId())
                .userId(order.getUserId())
                .customerId(order.getCustomer().getId())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getDateCreated())
                .status(order.getStatus())
                .orderItems(orderItemResponses)
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress().getSpecificAddress())
                .shippingDate(localDate)
                .paymentMethod(order.getPaymentMethod())
                .customerName(order.getCustomer().getFullName())
                .customerEmail(order.getCustomer().getEmail())
                .customerPhone(order.getCustomer().getPhoneNumber())
                .shippingCountry(order.getShippingAddress().getCountry())
                .shippingCity(order.getShippingAddress().getCity())
                .shippingDistrict(order.getShippingAddress().getDistrict())
                .dateCreated(order.getDateCreated())
                .build();
    }
}
