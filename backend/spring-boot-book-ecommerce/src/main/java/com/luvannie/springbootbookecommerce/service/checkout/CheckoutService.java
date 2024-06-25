package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.component.SecurityComponent;
import com.luvannie.springbootbookecommerce.dao.CouponRepository;
import com.luvannie.springbootbookecommerce.dao.CustomerRepository;
import com.luvannie.springbootbookecommerce.dao.OrderRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.responses.purchase.PurchaseResponse;
import com.luvannie.springbootbookecommerce.entity.*;
//import jakarta.transaction.Transactional;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service

public class CheckoutService implements ICheckoutService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final SecurityComponent securityComponent;



    public CheckoutService(CustomerRepository customerRepository, UserRepository userRepository,
                           OrderRepository orderRepository, CouponRepository couponRepository, SecurityComponent securityComponent
                          ) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.couponRepository = couponRepository;
        this.securityComponent = securityComponent;

    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(PurchaseDTO purchaseDTO) throws DataNotFoundException {

        // retrieve the order info from dto
        Order order = purchaseDTO.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchaseDTO.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with  shippingAddress
        order.setShippingAddress(purchaseDTO.getShippingAddress());

        //chuyen active thanh 1
        order.setActive(Boolean.TRUE);
        //chuyen status thanh pending
        order.setStatus(OrderStatus.PENDING);
        // populate order with user

        User user = userRepository
                .findById(purchaseDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+purchaseDTO.getUserId()));
        order.setUserId(user.getId());

        // populate customer with order
        Customer customer = purchaseDTO.getCustomer();
        customer.add(order);

        //check coupon
        String couponCode = purchaseDTO.getCouponCode();
        if (!couponCode.isEmpty()) {
            Coupon coupon = couponRepository.findByCode(couponCode)
                    .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));

            if (!coupon.isActive()) {
                throw new IllegalArgumentException("Coupon is not active");
            }

            order.setCoupon(coupon);
        } else {
            order.setCoupon(null);
        }

        Random rand = new Random();
        int randomNum = rand.nextInt((7 - 3) + 1) + 3;
        Calendar cal = Calendar.getInstance();
//        cal.setTime(order.getDateCreated());
        cal.add(Calendar.DATE, randomNum);
        order.setShippingDate(cal.getTime());

        // Set shippingMethod to "NORMAL" if it's null
        if (order.getShippingMethod() == null) {
            order.setShippingMethod("NORMAL");
        }

        if (order.getPaymentMethod() == null) {
            order.setPaymentMethod("VNPAY");
        }

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }



    private String generateOrderTrackingNumber() {
        //tao ngau nhien ma tracking number
        return UUID.randomUUID().toString();
    }

    //find order by user_id


}