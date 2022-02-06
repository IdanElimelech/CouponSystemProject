package com.jb.couponsystem.service;

import com.jb.couponsystem.controller.ex.CouponAlreadyOwnedException;
import com.jb.couponsystem.controller.ex.CouponAmountZero;
import com.jb.couponsystem.controller.ex.CouponDataError;
import com.jb.couponsystem.controller.ex.CustomerDataError;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.entity.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface CustomerService {

    Coupon purchaseCoupon (long couponId, long customerId) throws CouponDataError, CustomerDataError, CouponAlreadyOwnedException, CouponAmountZero;

    Optional<Customer> updateCustomer(Customer customer);

    Optional<List<Coupon>> getAllPurchasedCoupons(long id);

    Optional<List<Coupon>> getAllCoupons(long id);

    Optional<List<Coupon>> getAllCouponsByCategory(int category, long customerId);

    Optional<List<Coupon>> getAllCouponsLessThen(double price, long customerId);

    Optional<List<Coupon>> getAllCouponsBeforeDate(LocalDateTime date, long customerId);

    Optional<Customer> getCustomerById(long clientId);
}
