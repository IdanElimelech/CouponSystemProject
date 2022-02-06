package com.jb.couponsystem.service;

import com.jb.couponsystem.controller.ex.CouponAlreadyOwnedException;
import com.jb.couponsystem.controller.ex.CouponAmountZero;
import com.jb.couponsystem.controller.ex.CouponDataError;
import com.jb.couponsystem.controller.ex.CustomerDataError;
import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.entity.Customer;
import com.jb.couponsystem.repo.CouponRepository;
import com.jb.couponsystem.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceFcd implements CustomerService {
    private CouponRepository couponRepo;
    private CustomerRepository customerRepo;

    @Autowired
    public CustomerServiceFcd(CouponRepository couponRepo, CustomerRepository customerRepo) {
        this.couponRepo = couponRepo;
        this.customerRepo = customerRepo;
    }

    public CustomerServiceFcd() {
    }

    @Override
    public Optional<Customer> updateCustomer(Customer customer) {
        if (customer.getId() != 0) {
            return Optional.of(customerRepo.save(customer));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coupon>> getAllPurchasedCoupons(long id) {
        List<Coupon> cusPurchasedCoupons = couponRepo.findByCustomerId(id);
        if (!cusPurchasedCoupons.isEmpty()) {
            return Optional.of(cusPurchasedCoupons);
        }
        return Optional.empty();
    }

    /**
     * Get all coupons which are available for purchase
     *
     * @param customerId
     * @return all Coupons without already purchased Coupons
     */
    @Override
    public Optional<List<Coupon>> getAllCoupons(long customerId) {
        List<Coupon> allCoupons = couponRepo.findAll();
        if (!allCoupons.isEmpty()) {
            return Optional.of(filterPurchasedCoupons(allCoupons, customerId));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coupon>> getAllCouponsByCategory(int category, long customerId) {
        List<Coupon> couponsByCategory = couponRepo.findCouponsByCategory(category);
        if (!couponsByCategory.isEmpty()) {
            return Optional.of(filterPurchasedCoupons(couponsByCategory, customerId));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coupon>> getAllCouponsLessThen(double price, long customerId) {
        List<Coupon> byPriceLessThan = couponRepo.findByPriceLessThan(price);
        if (!byPriceLessThan.isEmpty()) {
            return Optional.of(filterPurchasedCoupons(byPriceLessThan, customerId));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coupon>> getAllCouponsBeforeDate(LocalDateTime endDate, long customerId) {
        List<Coupon> couponsBeforeEndDate = couponRepo.findByEndDateBefore(endDate);
        if (!couponsBeforeEndDate.isEmpty()) {
            return Optional.of(filterPurchasedCoupons(couponsBeforeEndDate, customerId));
        }
        return Optional.empty();
    }

    /**
     * an additional Function to filter the purchased coupons from any list
     *
     * @param unfilteredList - The list with purchased coupons
     * @param customerId     - The customer's i.d
     * @return - The list without the purchased coupons, only 'available'/'relevant' coupons
     */
    public List<Coupon> filterPurchasedCoupons(List<Coupon> unfilteredList, long customerId) {
        List<Long> purchasedCouponIds = new ArrayList<>();
        if (getAllPurchasedCoupons(customerId).isPresent()) {
            for (Coupon tempCoupon : getAllPurchasedCoupons(customerId).get()) {
                purchasedCouponIds.add(tempCoupon.getId());
            }
        }

        unfilteredList.removeIf(coupon -> purchasedCouponIds.contains(coupon.getId()));
        return unfilteredList;
    }

    public Optional<Customer> getCustomerById(long id) {
        return customerRepo.findById(id);
    }

    public Coupon purchaseCoupon(long couponId, long customerId) throws CouponDataError,
            CustomerDataError, CouponAlreadyOwnedException, CouponAmountZero {
        Optional<Coupon> optCoupon = couponRepo.findById(couponId);
        if (!optCoupon.isPresent()) {
            throw new CouponDataError("Coupon Id not found!");
        }
        Coupon selectedCoupon = optCoupon.get();

        Optional<Customer> optCustomer = customerRepo.findById(customerId);
        if (!optCustomer.isPresent()) {
            throw new CustomerDataError("Customer " + customerId + " Not Found");
        }
        Customer customer = optCustomer.get();

        if (!customer.getCoupons().isEmpty()) {
            for (Coupon c : customer.getCoupons()) {
                if (c.equals(selectedCoupon)) {
                    throw new CouponAlreadyOwnedException("The Coupon Selected for Purchase is already owned by Customer");
                }
            }
        }
        if (selectedCoupon.getAmount() > 0) {
            selectedCoupon.setAmount(selectedCoupon.getAmount() - 1);
            couponRepo.save(selectedCoupon);

            customer.getCoupons().add(selectedCoupon);
            customerRepo.save(customer);
            return selectedCoupon;
        }
        throw new CouponAmountZero("The Coupon Selected for Purchase is Over");


    }
}
