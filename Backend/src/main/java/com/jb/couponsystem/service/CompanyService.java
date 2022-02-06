package com.jb.couponsystem.service;

import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Coupon;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface CompanyService {

    Optional<List<Coupon>> sortCouponsBySalesAmount(long compId);

    Optional<List<Coupon>> getCompanyCoupons(long id);

    Optional<Long> removeCoupon(long id);

    Optional<Coupon> addCoupon(Coupon coupon);

    Optional<Coupon> getCoupon(long id);

    Optional<Coupon> updateCoupon(Coupon coupon);

    Optional<Company> updateCompany(Company company);

    Optional<List<Coupon>> getCompanyCouponsBeforeEndDate(LocalDateTime endDate, long companyId);

    Optional<List<Coupon>> getCompanyCouponsByCategory(int category, long companyId);

    Optional<List<Coupon>> getCompanyCouponLowerThanPrice(double price);

    Optional<Company> getCompanyById(long companyId);

}
