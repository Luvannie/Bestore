package com.luvannie.springbootbookecommerce.service.cart;

import com.luvannie.springbootbookecommerce.dao.BookRepository;
import com.luvannie.springbootbookecommerce.dao.CartItemRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.CartItemDTO;
import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.entity.CartItem;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.responses.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public CartItem createCartItem(CartItemDTO cartItem) throws Exception {
        //tim xem userId có tồn tại ko
        User user =userRepository.findById(cartItem.getUserId())
                .orElseThrow(() -> new Exception(
                        "Cannot find User with id : "+cartItem.getUserId()));
        // tim book theo id
        Book book = bookRepository.findById(cartItem.getBookId())
                .orElseThrow(() -> new Exception(
                        "Cannot find product with id: " + cartItem.getBookId()));
        CartItem cartItemDetail = CartItem.builder()
                .userId(cartItem.getUserId())
                .bookId(cartItem.getBookId())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .thumbnail(book.getThumbnail())
                .build();
        //lưu vào db
        return cartItemRepository.save(cartItemDetail);
    }

    @Override
    public CartItem getCartItem(Long cartItemId) throws Exception {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception(
                        "Cannot find CartItem with id : "+cartItemId));
    }

    @Override
    public CartItem updateCartItem(Long cartItemId, CartItemDTO cartItemDTO) throws Exception {
        //tim xem cartItem có tồn tại ko
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception(
                        "Cannot find CartItem with id : "+cartItemId));
        //tim xem userId có tồn tại ko
        User user =userRepository.findById(cartItemDTO.getUserId())
                .orElseThrow(() -> new Exception(
                        "Cannot find User with id : "+cartItemDTO.getUserId()));
        // tim book theo id
        Book book = bookRepository.findById(cartItemDTO.getBookId())
                .orElseThrow(() -> new Exception(
                        "Cannot find product with id: " + cartItemDTO.getBookId()));
        cartItem.setBookId(cartItemDTO.getBookId());
        if(cartItemDTO.getQuantity() != null){
            cartItem.setQuantity(cartItemDTO.getQuantity());
        };
        if(cartItemDTO.getUnitPrice() != null){
            cartItem.setUnitPrice(cartItemDTO.getUnitPrice());
        };
        if(cartItemDTO.getThumbnail() != null){
            cartItem.setThumbnail(cartItemDTO.getThumbnail());
        };
        return cartItemRepository.save(cartItem);

    }

    @Override
    public void deleteCartItem(Long cartItemId) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception(
                        "Cannot find CartItem with id : "+cartItemId));
        cartItemRepository.delete(cartItem);

    }

    @Override
    public List<CartItemResponse> findByUserId(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream().map(cartItem -> CartItemResponse.fromCartItem(cartItem)).toList();
    }
}
