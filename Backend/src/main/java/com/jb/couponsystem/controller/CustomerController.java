package com.jb.couponsystem.controller;

import com.jb.couponsystem.controller.ex.*;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.entity.Customer;
import com.jb.couponsystem.model.ClientSession;
import com.jb.couponsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {
    private CustomerService customerService;
    private Map<String, ClientSession> tokenMap;

    @Autowired
    public CustomerController(CustomerService customerService, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.customerService = customerService;
        this.tokenMap = tokensMap;
    }

    /**
     * Get all available coupons for the relevant customer
     *
     * @param token
     * @return All Available Coupons (Without already purchased coupons)
     * @throws InvalidTokenException
     * @throws NoContentFoundException
     */
    @GetMapping("/customers/coupons")
    private ResponseEntity<List<Coupon>> getAllCoupons(String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, the Token is Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> availableCoupons = customerService.getAllCoupons(clientSession.getClientId());
        if (availableCoupons.isPresent()) {
            if (!availableCoupons.get().isEmpty()) {
                return ResponseEntity.ok(availableCoupons.get());
            }
        }
        throw new NoContentFoundException("No Coupons found");
    }

    @GetMapping("/customers/coupons/purchased")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Purchased Coupons, the Token is possibly Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> customerPurchasedCoupons = customerService.getAllPurchasedCoupons(clientSession.getClientId());
        if (customerPurchasedCoupons.isPresent()) {
            if (!customerPurchasedCoupons.get().isEmpty()) {
                return ResponseEntity.ok(customerPurchasedCoupons.get());
            }
        }
        throw new NoContentFoundException("No Coupons Found for given user");
    }

    /**
     * A Function to search for coupons before given date
     *
     * @param endDate
     * @param token
     * @return All Coupons by given end date, without purchased coupons
     * @throws InvalidTokenException
     * @throws NoContentFoundException
     */
    @GetMapping("/customers/coupons/before")
    public ResponseEntity<List<Coupon>> getAllCouponsBeforeDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, the Token is Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> couponsBeforeEndDate = customerService.getAllCouponsBeforeDate(endDate, clientSession.getClientId());
        if (couponsBeforeEndDate.isPresent()) {
            if (!couponsBeforeEndDate.get().isEmpty()) {
                return ResponseEntity.ok(couponsBeforeEndDate.get());
            }
        }
        throw new NoContentFoundException("No Coupons Found With Given Date");
    }

    /**
     * A Function to search for coupons under given price
     *
     * @param price
     * @param token
     * @return All coupons under given price, without already owned coupons
     * @throws InvalidTokenException
     * @throws NoContentFoundException
     */
    @GetMapping("/customers/coupons/lower-than")
    public ResponseEntity<List<Coupon>> getCustomerCouponsLowerThanPrice(@RequestParam double price, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, the Token is Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> couponsLowerThanPrice = customerService.getAllCouponsLessThen(price, clientSession.getClientId());
        if (couponsLowerThanPrice.isPresent()) {
            if (!couponsLowerThanPrice.get().isEmpty()) {
                return ResponseEntity.ok(couponsLowerThanPrice.get());
            }
        }
        throw new NoContentFoundException("No Coupons found under given Price");
    }

    /**
     * a Function to search all relevant coupons by category
     *
     * @param category
     * @param token
     * @return coupons by category without already purchased coupons
     * @throws InvalidTokenException
     * @throws NoContentFoundException
     */
    @GetMapping("/customers/coupons/by-category")
    public ResponseEntity<List<Coupon>> getCustomerCouponsByCategory(@RequestParam int category, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, the Token is Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> cusCouponsByCategory = customerService.getAllCouponsByCategory(category, clientSession.getClientId());
        if (cusCouponsByCategory.isPresent()) {
            if (!cusCouponsByCategory.get().isEmpty()) {
                return ResponseEntity.ok(cusCouponsByCategory.get());
            }
        }
        throw new NoContentFoundException("No Coupons Found under given Category");
    }

    /**
     * Function to help the frontend fetch the relevant entity after login
     */
    @GetMapping("/customer")
    public ResponseEntity<Customer> fetchCustomerByToken(@RequestParam String token) {
        ClientSession clientSession = tokenMap.get(token);
        long clientId = clientSession.getClientId();
        Optional<Customer> customerById = customerService.getCustomerById(clientId);
        if (customerById.isPresent()) {
            return ResponseEntity.ok(customerById.get());
        }
        return ResponseEntity.noContent().build();
    }


    @PostMapping("customer/edit-profile")
    public ResponseEntity<Customer> updateCustomer(@RequestParam String firstName, @RequestParam String lastName,
                                                   @RequestParam String email, @RequestParam String password,
                                                   @RequestParam String token) throws InvalidTokenException, CustomerDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to Update Customer Profile, Token is Incorrect");
        }
        clientSession.access();

        Optional<Customer> optCustById = customerService.getCustomerById(clientSession.getClientId());
        if (optCustById.isPresent()) {
            Customer customerById = optCustById.get();
            customerById.setFirstName(firstName);
            customerById.setLastName(lastName);
            customerById.setEmail(email);
            customerById.setPassword(password);
            Optional<Customer> optPatchedCustomer = customerService.updateCustomer(customerById);
            if (optPatchedCustomer.isPresent()) {
                return ResponseEntity.ok(optPatchedCustomer.get());
            }
        }

        throw new CustomerDataError("Unable to Update Customer");
    }

    @PostMapping("customer/purchase")
    public ResponseEntity<Coupon> purchaseCoupon(@RequestParam long couponId, @RequestParam String token) throws InvalidTokenException
            , CouponDataError, CustomerDataError, CouponAmountZero, CouponAlreadyOwnedException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to purchase Coupon with given token");
        }
        clientSession.access();

        Coupon coupon = customerService.purchaseCoupon(couponId, clientSession.getClientId());
        return ResponseEntity.ok(coupon);
    }

    @DeleteMapping("customer/logout")
    public ResponseEntity<Long> logout(@RequestParam String token) {
        if (tokenMap.get(token) != null) {
            return ResponseEntity.ok(tokenMap.remove(token).getClientId());
        }
        return ResponseEntity.noContent().build();
    }

}
