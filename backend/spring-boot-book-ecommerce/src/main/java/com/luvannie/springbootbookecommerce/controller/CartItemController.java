package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.component.LocalizationUtils;
import com.luvannie.springbootbookecommerce.component.SecurityUtils;
import com.luvannie.springbootbookecommerce.dto.CartItemDTO;
import com.luvannie.springbootbookecommerce.entity.CartItem;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.cart.CartItemResponse;
import com.luvannie.springbootbookecommerce.service.cart.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequestMapping("/api/cart_item")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;
    private final SecurityUtils securityUtils;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> createCartItem(@RequestBody CartItemDTO cartItemDTO) throws Exception {
        CartItem newCartItem = cartItemService.createCartItem(cartItemDTO);
        CartItemResponse cartItemResponse = CartItemResponse.fromCartItem(newCartItem);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Create cart item successfully")
                        .status(HttpStatus.CREATED)
                        .data(cartItemResponse)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCartItem(@PathVariable("id") Long id) throws Exception {
        CartItem cartItem = cartItemService.getCartItem(id);
        CartItemResponse cartItemResponse = CartItemResponse.fromCartItem(cartItem);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Get cart item successfully")
                        .status(HttpStatus.OK)
                        .data(cartItemResponse)
                        .build()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getCartItems(@PathVariable("userId") Long userId) {
        User loginUser = securityUtils.getLoggedInUser();
        boolean isUserIdBlank = userId == null || userId <= 0;
        List<CartItemResponse> cartItemResponses = cartItemService.findByUserId(userId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Get cart items successfully")
                        .status(HttpStatus.OK)
                        .data(cartItemResponses)
                        .build()
        );
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> updateCartItem(@PathVariable("id") Long id, @RequestBody CartItemDTO cartItemDTO) throws Exception {
        CartItem updatedCartItem = cartItemService.updateCartItem(id, cartItemDTO);
        CartItemResponse cartItemResponse = CartItemResponse.fromCartItem(updatedCartItem);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Update cart item successfully")
                        .status(HttpStatus.OK)
                        .data(cartItemResponse)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deleteCartItem(@PathVariable("id") Long id) throws Exception {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Delete cart item successfully")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
