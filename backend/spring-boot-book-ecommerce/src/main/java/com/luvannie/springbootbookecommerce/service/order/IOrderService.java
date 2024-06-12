package com.luvannie.springbootbookecommerce.service.order;

import com.luvannie.springbootbookecommerce.dto.OrderDTO;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.responses.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order getOrderById(Long orderId);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long orderId);
    List<OrderResponse> findByUserId(Long userId);

    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);
}
