package com.jb.couponsystem.controller;

import com.jb.couponsystem.controller.ex.*;
import com.jb.couponsystem.entity.Admin;
import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.entity.Customer;
import com.jb.couponsystem.model.ClientSession;
import com.jb.couponsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AdminController {
    private AdminService adminService;
    private Map<String, ClientSession> tokenMap;

    @Autowired
    public AdminController(AdminService adminService, @Qualifier("tokens") Map<String, ClientSession> tokenMap) {
        this.adminService = adminService;
        this.tokenMap = tokenMap;
    }

    /* CRUD Company */
    @DeleteMapping("/admin/company/del")
    public ResponseEntity<Long> removeCompany(@RequestParam long compId, @RequestParam String token) throws InvalidTokenException, CompanyDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to remove Company, Admin's Token is Invalid");
        }
        clientSession.access();

        Optional<Long> deletedId = adminService.removeCompany(compId);
        if (deletedId.isPresent()) {
            return ResponseEntity.ok(deletedId.get());
        }
        throw new CompanyDataError("Unable to Remove Company, check your Inputs");
    }

    @PostMapping("/admin/company/add")
    public ResponseEntity<Company> addCompany(@RequestParam String name, @RequestParam String email,
                                              @RequestParam String password, @RequestParam String token) throws InvalidTokenException, CompanyDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to create Company, Admin's Token is Invalid");
        }
        clientSession.access();

        Company company = new Company(name, email, password);
        Optional<Company> optCompany = adminService.createCompany(company);
        if (optCompany.isPresent()) {
            return ResponseEntity.ok(optCompany.get());
        }
        throw new CompanyDataError("Unable to Create Company, check your Inputs");
    }

    @PostMapping("/admin/company/update")
    public ResponseEntity<Company> updateCompany(@RequestParam long id,
                                                 @RequestParam String name,
                                                 @RequestParam String email,
                                                 @RequestParam String password,
                                                 @RequestParam String token) throws InvalidTokenException, CompanyDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to update Company, Admin's Token is Invalid");
        }
        clientSession.access();

        Company company = new Company(id, name, email, password);
        Optional<Company> savedCompany = adminService.updateCompany(company);
        if (savedCompany.isPresent()) {
            return ResponseEntity.ok(savedCompany.get());
        }
        throw new CompanyDataError("Unable to Update Company, check your Inputs");
    }

    @GetMapping("/admin/company")
    public ResponseEntity<Company> getCompany(@RequestParam long companyId, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Company, Admin's Token is Invalid");
        }
        clientSession.access();

        Optional<Company> optCompany = adminService.getCompany(companyId);
        if (optCompany.isPresent()) {
            return ResponseEntity.ok(optCompany.get());
        }
        throw new NoContentFoundException("Company is no't found, check your inputs");
    }


    /* CRUD Coupon */
    @DeleteMapping("admin/coupon/del")
    public ResponseEntity<Long> removeCoupon(@RequestParam long couponId, @RequestParam String token) throws InvalidTokenException, CouponDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to remove Coupon, Admin's Token is Invalid");
        }
        clientSession.access();

        Optional<Long> deletedId = adminService.removeCoupon(couponId);
        if (deletedId.isPresent()) {
            return ResponseEntity.ok(deletedId.get());
        }
        throw new CouponDataError("Unable to Remove Coupon, check your Inputs");
    }

    @PostMapping("admin/coupon/new")
    public ResponseEntity<Coupon> addCoupon(@RequestParam String title,
                                            @RequestParam long companyId,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                            @RequestParam int category,
                                            @RequestParam int amount,
                                            @RequestParam String description,
                                            @RequestParam double price,
                                            @RequestParam String image,
                                            @RequestParam String token) throws InvalidTokenException, CouponDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to create Coupon, Admin's Token is Invalid");
        }
        clientSession.access();

        Coupon coupon = new Coupon(title, startDate, endDate, category, amount, description, price, image);
        Optional<Company> companyById = adminService.getCompany(companyId);
        if (companyById.isPresent()) {
            coupon.setCompany(companyById.get());
        }
        Optional<Coupon> optCoupon = adminService.createCoupon(coupon);
        if (optCoupon.isPresent()) {
            return ResponseEntity.ok(optCoupon.get());
        }
        throw new CouponDataError("Unable to Create Coupon, Check your Inputs");
    }

    @PostMapping("admin/coupon/update")
    public ResponseEntity<Coupon> updateCoupon(@RequestParam long id,
                                               @RequestParam String title,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                               @RequestParam int category,
                                               @RequestParam int amount,
                                               @RequestParam String description,
                                               @RequestParam double price,
                                               @RequestParam String image,
                                               @RequestParam String token) throws InvalidTokenException, CompanyDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to update Coupon, Admin's Token is Invalid");
        }
        clientSession.access();

        Coupon coupon = new Coupon(id, title, startDate, endDate, category, amount, description, price, image);

        Optional<Coupon> optDbCoupon = adminService.getCoupon(id);
        if (optDbCoupon.isPresent()) {
            coupon.setCompany(optDbCoupon.get().getCompany());
        }

        Optional<Coupon> updatedCoupon = adminService.updateCoupon(coupon);
        if (updatedCoupon.isPresent()) {
            return ResponseEntity.ok(updatedCoupon.get());
        }
        throw new CompanyDataError("Unable to Update Company, check your Inputs");
    }


    @GetMapping("admin/coupon")
    public ResponseEntity<Coupon> getCoupon(@RequestParam long couponId, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupon, Admin's Token is Invalid");
        }
        clientSession.access();

        Optional<Coupon> optCoupon = adminService.getCoupon(couponId);
        if (optCoupon.isPresent()) {
            return ResponseEntity.ok(optCoupon.get());
        }
        throw new NoContentFoundException("Coupon isn't Found, Check your Inputs");
    }

    /* CRUD Customer */
    @DeleteMapping("admin/customer/del")
    public ResponseEntity<Long> removeCustomer(@RequestParam long customerId, @RequestParam String token) throws InvalidTokenException, CustomerDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to remove Customer, Admin's Token is Invalid");
        }
        clientSession.access();

        Optional<Long> deletedId = adminService.removeCustomer(customerId);
        if (deletedId.isPresent()) {
            return ResponseEntity.ok(deletedId.get());
        }
        throw new CustomerDataError("Unable to Remove Customer, Check your Inputs");
    }

    @PostMapping("admin/customer/new")
    public ResponseEntity<Customer> addCustomer(@RequestParam String firstName,
                                                @RequestParam String lastName,
                                                @RequestParam String email,
                                                @RequestParam String password,
                                                @RequestParam String token) throws InvalidTokenException, CustomerDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to create Customer, Admin's Token is Invalid");
        }
        clientSession.access();

        Customer customer = new Customer(firstName, lastName, email, password);
        Optional<Customer> optCustomer = adminService.createCustomer(customer);
        if (optCustomer.isPresent()) {
            return ResponseEntity.ok(optCustomer.get());
        }
        throw new CustomerDataError("Unable to create Customer, check your Inputs");
    }

    @PostMapping("admin/customer/update")
    public ResponseEntity<Customer> updateCustomer(@RequestParam long customerId,
                                                   @RequestParam String firstName,
                                                   @RequestParam String lastName,
                                                   @RequestParam String email,
                                                   @RequestParam String password,
                                                   @RequestParam String token) throws InvalidTokenException, CustomerDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to update Customer, Admin's Token is Invalid");
        }
        clientSession.access();

        Customer customer = new Customer(customerId, firstName, lastName, email, password);
        Optional<Customer> optCustomer = adminService.updateCustomer(customer);
        if (optCustomer.isPresent()) {
            return ResponseEntity.ok(optCustomer.get());
        }
        throw new CustomerDataError("Unable to Update Customer, Check your Inputs");
    }

    @GetMapping("admin/customer")
    public ResponseEntity<Customer> getCustomer(@RequestParam long customerId, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Customer, Admin's Token is Invalid");
        }
        clientSession.access();

        Optional<Customer> optCustomer = adminService.getCustomer(customerId);
        if (optCustomer.isPresent()) {
            return ResponseEntity.ok(optCustomer.get());
        }
        throw new NoContentFoundException("Customer isn't found, check your Inputs");
    }

    /**
     * Function to help the frontend fetch the relevant entity after login
     */
    @GetMapping("/admin")
    private ResponseEntity<Admin> fetchAdminByToken(@RequestParam String token) {
        ClientSession clientSession = tokenMap.get(token);
        long clientId = clientSession.getClientId();
        Optional<Admin> adminById = adminService.getAdminById(clientId);
        if (adminById.isPresent()) {
            return ResponseEntity.ok(adminById.get());
        }
        return ResponseEntity.noContent().build();
    }

}
