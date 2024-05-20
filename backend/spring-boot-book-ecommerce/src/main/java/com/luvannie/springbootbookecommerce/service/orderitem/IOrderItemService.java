package com.luvannie.springbootbookecommerce.service.orderitem;

import com.luvannie.springbootbookecommerce.dto.OrderItemDTO;
import com.luvannie.springbootbookecommerce.entity.OrderItem;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;

import java.util.List;

public interface IOrderItemService {
    OrderItem createOrderItem(OrderItemDTO orderItem) throws Exception;
    OrderItem getOrderItem(Long orderItemId) throws DataNotFoundException;

    OrderItem updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO) throws DataNotFoundException;

    void deleteOrderItem(Long orderItemId) throws DataNotFoundException;

    List<OrderItem> findByOrderId(Long orderId);
}
