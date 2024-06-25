package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.component.LocalizationComponent;
import com.luvannie.springbootbookecommerce.component.SecurityComponent;
import com.luvannie.springbootbookecommerce.dto.OrderDTO;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.ResponsePageObject;
import com.luvannie.springbootbookecommerce.responses.order.OrderResponse;
import com.luvannie.springbootbookecommerce.service.order.OrderService;
import com.luvannie.springbootbookecommerce.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final LocalizationComponent localizationComponent;
    private final SecurityComponent securityComponent;




    // Other methods...

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ResponseObject> getOrders( @PathVariable("user_id") Long userId) {
        User loginUser = securityComponent.getLoggedInUser();
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
        String message = localizationComponent.getLocalizedMessage(
                MessageKeys.DELETE_ORDER_SUCCESSFULLY, id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(message)
                        .build()
        );
    }

    @GetMapping("/active/{userId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<Order>> getActiveOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getActiveOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        OrderResponse orderResponse = OrderResponse.fromOrder(order);

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Get order successfully")
                .status(HttpStatus.OK)
                .data(orderResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateOrder(
             @PathVariable long id,
             @RequestBody OrderDTO orderDTO) throws Exception {

        Order order = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(new ResponseObject("Update order successfully", HttpStatus.OK, order));
    }

    @GetMapping("/get-orders-by-keyword")
    public ResponseEntity<ResponsePageObject> getOrdersByKeyword(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );
        Page<OrderResponse> orderPage = orderService
                .getOrdersByKeyword(keyword, pageRequest)
                .map(OrderResponse::fromOrder);
        // Lấy tổng số trang
        int totalPages = orderPage.getTotalPages();
        List<OrderResponse> orderResponses = orderPage.getContent();
        return ResponseEntity.ok().body(ResponsePageObject.builder()
                .message("Get orders successfully")
                .status(HttpStatus.OK)
                .data(orderResponses)
                .totalPages(totalPages)
                .build());
    }
}