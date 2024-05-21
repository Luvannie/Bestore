package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.component.LocalizationUtils;
import com.luvannie.springbootbookecommerce.component.SecurityUtils;
import com.luvannie.springbootbookecommerce.dto.OrderDTO;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.order.OrderResponse;
import com.luvannie.springbootbookecommerce.service.order.OrderService;
import com.luvannie.springbootbookecommerce.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final LocalizationUtils localizationUtils;
    private final SecurityUtils securityUtils;




    // Other methods...

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getOrders( @PathVariable("user_id") Long userId) {
        User loginUser = securityUtils.getLoggedInUser();
        boolean isUserIdBlank = userId == null || userId <= 0;
        List<OrderResponse> orderResponses = orderService.findByUserId(isUserIdBlank ? loginUser.getId() : userId);
        return ResponseEntity.ok(ResponseObject
                .builder()
                .message("Get list of orders successfully")
                .data(orderResponses)
                .status(HttpStatus.OK)
                .build());
    }


    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteOrder( @PathVariable Long id) {
        //xóa mềm => cập nhật trường active = false
        orderService.deleteOrder(id);
        String message = localizationUtils.getLocalizedMessage(
                MessageKeys.DELETE_ORDER_SUCCESSFULLY, id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(message)
                        .build()
        );
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<List<Order>> getActiveOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getActiveOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateOrder(
             @PathVariable long id,
             @RequestBody OrderDTO orderDTO) throws Exception {

        Order order = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(new ResponseObject("Update order successfully", HttpStatus.OK, order));
    }
}