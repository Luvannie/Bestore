package com.luvannie.springbootbookecommerce.controller;
import com.luvannie.springbootbookecommerce.component.LocalizationUtils;
import com.luvannie.springbootbookecommerce.dto.OrderItemDTO;
import com.luvannie.springbootbookecommerce.entity.OrderItem;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.order.OrderItemResponse;
import com.luvannie.springbootbookecommerce.service.orderitem.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.luvannie.springbootbookecommerce.utils.MessageKeys;


import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final LocalizationUtils localizationUtils;

    public OrderItemController(OrderItemService orderItemService, LocalizationUtils localizationUtils) {
        this.orderItemService = orderItemService;
        this.localizationUtils = localizationUtils;
    }

    @PostMapping("")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> createOrderItem( @RequestBody OrderItemDTO orderItemDTO) throws Exception {
        OrderItem newOrderItem = orderItemService.createOrderItem(orderItemDTO);
        OrderItemResponse orderItemResponse = OrderItemResponse.fromOrderItem(newOrderItem);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Create order item successfully")
                        .status(HttpStatus.CREATED)
                        .data(orderItemResponse)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderItem( @PathVariable("id") Long id) throws DataNotFoundException {
        OrderItem orderItem = orderItemService.getOrderItem(id);
        OrderItemResponse orderItemResponse = OrderItemResponse.fromOrderItem(orderItem);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Get order item successfully")
                        .status(HttpStatus.OK)
                        .data(orderItemResponse)
                        .build()
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseObject> getOrderItems( @PathVariable("orderId") Long orderId) {
        List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
        List<OrderItemResponse> orderItemResponses = orderItems
                .stream()
                .map(OrderItemResponse::fromOrderItem)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Get order items by orderId successfully")
                        .status(HttpStatus.OK)
                        .data(orderItemResponses)
                        .build()
        );
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> updateOrderItem( @PathVariable("id") Long id, @RequestBody OrderItemDTO orderItemDTO) throws DataNotFoundException, Exception {
        OrderItem orderItem = orderItemService.updateOrderItem(id, orderItemDTO);
        return ResponseEntity.ok().body(ResponseObject
                .builder()
                .data(orderItem)
                .message("Update order item successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deleteOrderItem( @PathVariable("id") Long id) throws DataNotFoundException {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok()
                .body(ResponseObject.builder()
                        .message(localizationUtils
                                .getLocalizedMessage(MessageKeys.DELETE_ORDER_ITEM_SUCCESSFULLY))
                        .build());
    }
}
