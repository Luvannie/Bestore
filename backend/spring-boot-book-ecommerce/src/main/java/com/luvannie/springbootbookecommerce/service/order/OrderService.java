package com.luvannie.springbootbookecommerce.service.order;

import com.luvannie.springbootbookecommerce.dao.*;
import com.luvannie.springbootbookecommerce.dto.OrderDTO;
import com.luvannie.springbootbookecommerce.entity.Coupon;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.responses.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

//


    // Other methods...

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setActive(Boolean.FALSE);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> findByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(order -> OrderResponse.fromOrder(order)).toList();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

//
    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
    Order order = orderRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("Cannot find order with id: " + id));
    User existingUser = userRepository.findById(
                orderDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + id));
        if (orderDTO.getUserId() != null) {
            User user = new User();
            user.setId(orderDTO.getUserId());
            order.setUserId(user.getId());
        }

        if (orderDTO.getTotalPrice() != null) {
            order.setTotalPrice(orderDTO.getTotalPrice());
        }

        if (orderDTO.getDateCreated() != null) {
            order.setDateCreated(orderDTO.getDateCreated());
        }

        if (orderDTO.getCouponCode() != null) {
            Coupon coupon = couponRepository.findByCode(orderDTO.getCouponCode()).orElse(null);
            order.setCoupon(coupon);
        }

        if(orderDTO.getStatus()!= null) {
            order.setStatus(orderDTO.getStatus());
        }

    return orderRepository.save(order);
}

    public List<Order> getActiveOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdAndActiveNot(userId, Boolean.FALSE);
    }

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword, pageable);
    }
}