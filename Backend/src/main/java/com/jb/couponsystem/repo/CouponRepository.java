package com.jb.couponsystem.repo;

import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("from Coupon c inner join c.customers cu where cu.id=:customerId")
    List<Coupon> findByCustomerId(long customerId);

    @Query("select c from Customer as c inner join c.coupons as coupon where coupon.id=:couponId")
    List<Customer> findByCouponId(long couponId);

    List<Coupon> findCouponsByCategory(int category);

    List<Coupon> findByPriceLessThan(double price);

    List<Coupon> findByEndDateBefore(LocalDateTime endDate);

    Optional<List<Coupon>> findByCompanyId(long id);

    Optional<List<Coupon>> findByCompanyIdAndCategory(long companyId, int category);
}
