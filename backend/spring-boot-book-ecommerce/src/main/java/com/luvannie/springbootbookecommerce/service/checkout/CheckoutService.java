package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.dao.CouponRepository;
import com.luvannie.springbootbookecommerce.dao.CustomerRepository;
import com.luvannie.springbootbookecommerce.dao.OrderRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.dto.PurchaseResponse;
import com.luvannie.springbootbookecommerce.entity.*;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService implements ICheckoutService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;


    @Override
    @Transactional
    public PurchaseResponse placeOrder(PurchaseDTO purchaseDTO) {

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

        // populate order with user
        User user = purchaseDTO.getUser();

        Optional<User> optionalUser = userRepository.findByAccount(user.getAccount());
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }
        else{
            user = userRepository.save(user);
        }
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