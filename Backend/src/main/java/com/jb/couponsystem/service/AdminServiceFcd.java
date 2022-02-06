package com.jb.couponsystem.service;

import com.jb.couponsystem.entity.Admin;
import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.entity.Customer;
import com.jb.couponsystem.repo.AdminRepository;
import com.jb.couponsystem.repo.CompanyRepository;
import com.jb.couponsystem.repo.CouponRepository;
import com.jb.couponsystem.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceFcd implements AdminService {
    private CouponRepository couponRepo;
    private CompanyRepository companyRepo;
    private CustomerRepository customerRepo;
    private AdminRepository adminRepo;

    @Autowired
    public AdminServiceFcd(CouponRepository couponRepo, CompanyRepository companyRepo, CustomerRepository customerRepo, AdminRepository adminRepo) {
        this.couponRepo = couponRepo;
        this.companyRepo = companyRepo;
        this.customerRepo = customerRepo;
        this.adminRepo = adminRepo;
    }

    /* CRUD Company */
    @Override
    public Optional<Long> removeCompany(long compId) {
        Optional<Company> optCompany = companyRepo.findById(compId);
        if (optCompany.isPresent()) {
            companyRepo.deleteById(compId);
            return Optional.of(compId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Company> createCompany(Company company) {
        /* Check if there is a Company on DB with the given Email */
        Optional<Company> emailDuplicationCheck = companyRepo.findByEmail(company.getEmail());
        /* If the I.D !=0 and there is a company with the argument Company's email -> will return empty*/
        if (company.getId() != 0 || emailDuplicationCheck.isPresent()) {
            return Optional.empty();
        }
        Company savedCompany = companyRepo.save(company);
        return Optional.of(savedCompany);
    }

    @Override
    public Optional<Company> updateCompany(Company company) {
        if (company.getId() == 0) {
            return Optional.empty();
        }
        Company savedCompany = companyRepo.save(company);
        return Optional.of(savedCompany);
    }

    public Optional<Company> getCompany(long compId) {
        return companyRepo.findById(compId);
    }

    /* CRUD Coupon */
    @Override
    public Optional<Long> removeCoupon(long couponId) {
        Optional<Coupon> optCoupon = couponRepo.findById(couponId);
        if (optCoupon.isPresent()) {
            couponRepo.deleteById(couponId);
            return Optional.of(couponId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Coupon> createCoupon(Coupon coupon) {
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

    public Optional<Coupon> getCoupon(long couponId) {
        return couponRepo.findById(couponId);

    }

    /* CRUD Customer */
    @Override
    public Optional<Long> removeCustomer(long customerId) {
        Optional<Customer> byId = customerRepo.findById(customerId);
        if (byId.isPresent()) {
            customerRepo.deleteById(customerId);
            return Optional.of(customerId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> createCustomer(Customer customer) {
        Optional<Customer> emailDuplicationCheck = customerRepo.findByEmail(customer.getEmail());
        if (customer.getId() != 0 || emailDuplicationCheck.isPresent()) {
            return Optional.empty();
        }
        Customer savedCustomer = customerRepo.save(customer);
        return Optional.of(savedCustomer);
    }

    @Override
    public Optional<Customer> updateCustomer(Customer customer) {
        if (customer.getId() == 0) {
            return Optional.empty();
        }
        Customer savedCustomer = customerRepo.save(customer);
        return Optional.of(savedCustomer);
    }

    public Optional<Customer> getCustomer(long customerId) {
        return customerRepo.findById(customerId);
    }

    @Override
    public Optional<Admin> getAdminById(long id) {
        return adminRepo.findById(id);
    }
}
