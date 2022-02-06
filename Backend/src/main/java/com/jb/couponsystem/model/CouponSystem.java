package com.jb.couponsystem.model;

import com.jb.couponsystem.entity.Admin;
import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Customer;
import com.jb.couponsystem.controller.ex.InvalidLoginException;
import com.jb.couponsystem.repo.AdminRepository;
import com.jb.couponsystem.repo.CompanyRepository;
import com.jb.couponsystem.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponSystem {

    private static final int LOGIN_TYPE_CUSTOMER = 1;
    private static final int LOGIN_TYPE_COMPANY = 2;
    private static final int LOGIN_TYPE_ADMIN = 3;

    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;
    private AdminRepository adminRepository;

    @Autowired
    public CouponSystem(CustomerRepository customerRepo, CompanyRepository companyRepo, AdminRepository adminRepo) {
        this.customerRepository = customerRepo;
        this.companyRepository = companyRepo;
        this.adminRepository = adminRepo;
    }

    public ClientSession createSession(String email, String password, int logType) throws InvalidLoginException {
        switch (logType){

            case LOGIN_TYPE_CUSTOMER:
                Optional<Customer> optCustomer = customerRepository.findByEmailAndPassword(email, password);
                if (optCustomer.isPresent()) {
                    return ClientSession.create(optCustomer.get().getId(), LOGIN_TYPE_CUSTOMER);
                }
                break;

            case LOGIN_TYPE_COMPANY:
                Optional<Company> optCompany = companyRepository.findByEmailAndPassword(email, password);
                if (optCompany.isPresent()) {
                    return ClientSession.create(optCompany.get().getId(), LOGIN_TYPE_COMPANY);
                }
                break;

            case LOGIN_TYPE_ADMIN:
                Optional<Admin> optAdmin = adminRepository.findByEmailAndPassword(email, password);
                if (optAdmin.isPresent()) {
                    return ClientSession.create(optAdmin.get().getId(), LOGIN_TYPE_ADMIN);
                }
                break;

            default:
        }

        throw new InvalidLoginException("Unable to Login with given email or password");
    }
}
