package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.component.SecurityUtils;
import com.luvannie.springbootbookecommerce.dao.CouponRepository;
import com.luvannie.springbootbookecommerce.dao.CustomerRepository;
import com.luvannie.springbootbookecommerce.dao.OrderRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.PaymentInfoDTO;
import com.luvannie.springbootbookecommerce.dto.PurchaseDTO;
import com.luvannie.springbootbookecommerce.responses.purchase.PurchaseResponse;
import com.luvannie.springbootbookecommerce.entity.*;
//import jakarta.transaction.Transactional;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
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
    private final SecurityUtils securityUtils;



    public CheckoutService(CustomerRepository customerRepository, UserRepository userRepository,
                           OrderRepository orderRepository, CouponRepository couponRepository, SecurityUtils securityUtils,
                           @Value("${STRIPE_SECRET_KEY}" ) String stripeSecretKey) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.couponRepository = couponRepository;
        this.securityUtils = securityUtils;
        Stripe.apiKey = stripeSecretKey;
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

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfoDTO paymentInfoDTO) throws StripeException {
        List<String> PaymentMethodTypes = new ArrayList<>();
        PaymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoDTO.getAmount());
        params.put("currency", paymentInfoDTO.getCurrency());
        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        //tao ngau nhien ma tracking number
        return UUID.randomUUID().toString();
    }

    //find order by user_id


}