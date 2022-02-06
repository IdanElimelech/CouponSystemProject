package com.jb.couponsystem.controller;

import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Component
@EnableScheduling
public class ExpiredCouponsMaintenance {
    private CouponRepository couponRepo;

    @Autowired
    public ExpiredCouponsMaintenance(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Scheduled(cron = "0 2 * * * *")
    public void removeExpiredCoupons() {
        List<Coupon> dbCoupons = couponRepo.findAll();
        for (Coupon coupon : dbCoupons) {
            if (coupon.getEndDate().isBefore(LocalDateTime.now())) {
                couponRepo.delete(coupon);
            }
        }

    }
}
