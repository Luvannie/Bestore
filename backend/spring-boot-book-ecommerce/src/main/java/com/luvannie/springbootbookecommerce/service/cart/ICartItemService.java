package com.luvannie.springbootbookecommerce.service.cart;

import com.luvannie.springbootbookecommerce.dto.CartItemDTO;
import com.luvannie.springbootbookecommerce.entity.CartItem;
import com.luvannie.springbootbookecommerce.responses.cart.CartItemResponse;

import java.util.List;

public interface ICartItemService {
    CartItem createCartItem(CartItemDTO cartItem) throws Exception;
    CartItem getCartItem(Long cartItemId) throws Exception;
    CartItem updateCartItem(Long cartItemId, CartItemDTO cartItemDTO) throws Exception;
    void deleteCartItem(Long cartItemId) throws Exception;
    List<CartItemResponse> findByUserId(Long userId);

}
