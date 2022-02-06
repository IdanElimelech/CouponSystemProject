package com.jb.couponsystem.service;

import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.repo.CompanyRepository;
import com.jb.couponsystem.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceFcd implements CompanyService {
    private CouponRepository couponRepo;
    private CompanyRepository companyRepo;

    @Autowired
    public CompanyServiceFcd(CouponRepository couponRepo, CompanyRepository companyRepo) {
        this.couponRepo = couponRepo;
        this.companyRepo = companyRepo;
    }

    public Optional<Company> updateCompany(Company company) {
        if (company.getId() != 0) {
            return Optional.of(companyRepo.save(company));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coupon>> getCompanyCoupons(long id) {
        return couponRepo.findByCompanyId(id);
    }

    @Override
    public Optional<Long> removeCoupon(long id) {
        Optional<Coupon> dbCoupon = couponRepo.findById(id);
        if (dbCoupon.isPresent()) {
            couponRepo.deleteById(id);
            return Optional.of(id);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Coupon> addCoupon(Coupon coupon) {
        if (coupon.getId() != 0) {
            return Optional.empty();
        }
        return Optional.of(couponRepo.save(coupon));
    }

    @Override
    public Optional<Coupon> updateCoupon(Coupon coupon) {
        if (coupon.getId() == 0) {
            return Optional.empty();
        }
        return Optional.of(couponRepo.save(coupon));
    }

    /**
     * Collect all coupons before given date, and filter only those which belong to the company in use
     *
     * @param endDate   date selected
     * @param companyId working companies id
     * @return Optional List
     */
    @Override
    public Optional<List<Coupon>> getCompanyCouponsBeforeEndDate(LocalDateTime endDate, long companyId) {
        if (endDate != null) {
            List<Coupon> couponsBeforeEndDate = couponRepo.findByEndDateBefore(endDate);
            if (!couponsBeforeEndDate.isEmpty()) {
                List<Coupon> companyCouponsBeforeDate = couponsBeforeEndDate.stream()
                        .filter(coupon -> coupon.getCompany().getId() == companyId)
                        .collect(Collectors.toList());
                return Optional.of(companyCouponsBeforeDate);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coupon>> getCompanyCouponsByCategory(int category, long companyId) {
        return couponRepo.findByCompanyIdAndCategory(companyId, category);
    }

    @Override
    public Optional<List<Coupon>> getCompanyCouponLowerThanPrice(double price) {
        List<Coupon> byPriceLessThan = couponRepo.findByPriceLessThan(price);
        if (!byPriceLessThan.isEmpty()) {
            return Optional.of(byPriceLessThan);
        }
        return Optional.empty();
    }

    public Optional<Coupon> getCoupon(long id) {
        return couponRepo.findById(id);
    }

    public Optional<Company> getCompanyById(long companyId) {
        return companyRepo.findById(companyId);
    }

    public Optional<List<Coupon>> sortCouponsBySalesAmount(long compId) {
        Optional<List<Coupon>> optCompanyCoupons = getCompanyCoupons(compId);
        if (optCompanyCoupons.isPresent()) {
            List<Coupon> sortedList = optCompanyCoupons.get().stream()
                    .sorted((o1, o2) -> {
                        int coupon1sold = couponRepo.findByCouponId(o1.getId()).size();
                        int coupon2sold = couponRepo.findByCouponId(o2.getId()).size();
                        if (coupon1sold > coupon2sold) {
                            return -1;
                        }
                        return 1;
                    }).collect(Collectors.toList());
            return Optional.of(sortedList);
        }
        return Optional.empty();
    }
}