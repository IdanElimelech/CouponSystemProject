package com.jb.couponsystem.service;

import com.jb.couponsystem.entity.Admin;
import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.entity.Customer;

import java.util.Optional;

public interface AdminService {

    /* CRUD Company */
    Optional<Long> removeCompany(long compId);

    Optional<Company> createCompany(Company company);

    Optional<Company> updateCompany(Company company);

    Optional<Company> getCompany(long compId);

    /* CRUD Coupon */
    Optional<Long> removeCoupon(long couponId);

    Optional<Coupon> createCoupon(Coupon coupon);

    Optional<Coupon> updateCoupon(Coupon coupon);

    Optional<Coupon> getCoupon(long couponId);

    /* CRUD Customer */
    Optional<Long> removeCustomer(long customerId);

    Optional<Customer> createCustomer(Customer customer);

    Optional<Customer> updateCustomer(Customer customer);

    Optional<Customer> getCustomer(long customerId);

    /* Frontend assistance */
    Optional<Admin> getAdminById(long id);
}
