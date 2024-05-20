package com.luvannie.springbootbookecommerce.service.coupon;

public interface ICouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
}
