package com.luvannie.springbootbookecommerce.service.order;

import com.luvannie.springbootbookecommerce.dao.OrderRepository;
import com.luvannie.springbootbookecommerce.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Other methods...

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setActive(Boolean.FALSE);
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    public List<Order> getActiveOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdAndActiveNot(userId, Boolean.FALSE);
    }
}