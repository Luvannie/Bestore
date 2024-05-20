package com.luvannie.springbootbookecommerce.service.checkout;

import com.luvannie.springbootbookecommerce.dao.CustomerRepository;
import com.luvannie.springbootbookecommerce.dao.OrderRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.Purchase;
import com.luvannie.springbootbookecommerce.dto.PurchaseResponse;
import com.luvannie.springbootbookecommerce.entity.Customer;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.OrderItem;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.service.checkout.CheckoutService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;

    private OrderRepository orderRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, OrderRepository orderRepository){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with  shippingAddress
        order.setShippingAddress(purchase.getShippingAddress());

        //chuyen active thanh 1
        order.setActive(Boolean.TRUE);

        // populate order with user
        User user = purchase.getUser();

        Optional<User> optionalUser = userRepository.findByAccount(user.getAccount());
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }
        else{
            user = userRepository.save(user);
        }
        order.setUserId(user.getId());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

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