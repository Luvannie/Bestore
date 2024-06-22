package com.luvannie.springbootbookecommerce.dao;

import com.luvannie.springbootbookecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findByUserIdAndActiveNot(Long userId, Boolean active);



    @Query(value = "SELECT o.*, u.username, u.email FROM orders o " +
            "JOIN users u ON o.user_id = u.id " +
            "WHERE (u.username LIKE %:keyword% OR u.email LIKE %:keyword% OR o.order_tracking_number LIKE %:keyword%)",
            countQuery = "SELECT count(*) FROM orders o " +
                    "JOIN users u ON o.user_id = u.id " +
                    "WHERE (u.username LIKE %:keyword% OR u.email LIKE %:keyword% OR o.order_tracking_number LIKE %:keyword%)",
            nativeQuery = true)
    Page<Order> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
