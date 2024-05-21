package com.luvannie.springbootbookecommerce.service.orderitem;

import com.luvannie.springbootbookecommerce.dao.BookRepository;
import com.luvannie.springbootbookecommerce.dao.OrderItemRepository;
import com.luvannie.springbootbookecommerce.dao.OrderRepository;
import com.luvannie.springbootbookecommerce.dto.OrderItemDTO;
import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.OrderItem;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemService implements IOrderItemService{
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public OrderItem createOrderItem(OrderItemDTO orderItem) throws Exception {
        //tìm xem orderId có tồn tại ko
        Order order = orderRepository.findById(orderItem.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find Order with id : "+orderItem.getOrderId()));
        // Tìm Product theo id
        Book book = bookRepository.findById(orderItem.getBookId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderItem.getBookId()));
        OrderItem orderDetail = OrderItem.builder()
                .order(order)
                .bookId(book.getId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .build();
        //lưu vào db
        return orderItemRepository.save(orderDetail);
    }

    @Override
    public OrderItem getOrderItem(Long orderItemId) throws DataNotFoundException {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find OrderItem with id : "+orderItemId));
    }

    @Override
    @Transactional
    public OrderItem updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO) throws DataNotFoundException {
        //tim xem orderItem có tồn tại ko
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find OrderItem with id : "+orderItemId));
        //tìm xem orderId có tồn tại ko
        Order order = orderRepository.findById(orderItemDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find Order with id : "+orderItemDTO.getOrderId()));
        // Tìm Book theo id
        Book book = bookRepository.findById(orderItemDTO.getBookId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderItemDTO.getBookId()));
        orderItem.setOrder(order);
        orderItem.setBookId(book.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
        return orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long orderItemId) throws DataNotFoundException {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find OrderItem with id : "+orderItemId));
        orderItemRepository.delete(orderItem);

    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

}
