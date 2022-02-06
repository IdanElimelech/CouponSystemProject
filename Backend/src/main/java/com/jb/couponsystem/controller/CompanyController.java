package com.jb.couponsystem.controller;

import com.jb.couponsystem.controller.ex.CompanyUpdateError;
import com.jb.couponsystem.controller.ex.CouponDataError;
import com.jb.couponsystem.controller.ex.InvalidTokenException;
import com.jb.couponsystem.controller.ex.NoContentFoundException;
import com.jb.couponsystem.entity.Company;
import com.jb.couponsystem.entity.Coupon;
import com.jb.couponsystem.model.ClientSession;
import com.jb.couponsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CompanyController {
    private CompanyService compService;
    private Map<String, ClientSession> tokenMap;

    @Autowired
    public CompanyController(CompanyService compService, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.compService = compService;
        this.tokenMap = tokensMap;
    }

    @GetMapping("/companies/coupons")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@RequestParam String token) {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.access();

        Optional<List<Coupon>> optCoupons = compService.getCompanyCoupons(clientSession.getClientId());
        if (optCoupons.isPresent()) {
            return ResponseEntity.ok(optCoupons.get());
        }
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/companies/coupon/del")
    public ResponseEntity<Long> removeCoupon(@RequestParam long id, @RequestParam String token) throws InvalidTokenException, CouponDataError {
        ClientSession clientSession = tokenMap.get(token);

        if (clientSession == null) {
            throw new InvalidTokenException("Unable to remove Coupon, the Token is Incorrect");
        }
        clientSession.access();

        Optional<Long> optCPID = compService.removeCoupon(id);
        if (optCPID.isPresent()) {
            return ResponseEntity.ok(optCPID.get());
        }
        throw new CouponDataError("Unable to remove Coupon, the I.D may be wrong");
    }

    @PostMapping("companies/coupons/new")
    public ResponseEntity<Coupon> addCoupon(@RequestParam String title,
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
            throw new InvalidTokenException("Unable to create Coupon, the Token is Incorrect");
        }
        clientSession.access();

        Coupon coupon = new Coupon(title, startDate, endDate, category, amount, description, price, image);
        Optional<Company> companyById = compService.getCompanyById(clientSession.getClientId());
        if (companyById.isPresent()) {
            coupon.setCompany(companyById.get());
        }
        Optional<Coupon> optCoupon = compService.addCoupon(coupon);
        if (optCoupon.isPresent()) {
            return ResponseEntity.ok(optCoupon.get());
        }
        throw new CouponDataError("Unable to create Coupon, Check your inputs");
    }

    @PostMapping("companies/edit-profile")
    public ResponseEntity<Company> updateCompany(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String token) throws InvalidTokenException, CompanyUpdateError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to Update Company Profile, Token is Incorrect");
        }
        clientSession.access();

        Optional<Company> optCompById = compService.getCompanyById(clientSession.getClientId());
        if (optCompById.isPresent()) {
            Company compById = optCompById.get();
            compById.setName(name);
            compById.setEmail(email);
            compById.setPassword(password);
            Optional<Company> patchedComp = compService.updateCompany(compById);
            if (patchedComp.isPresent()) {
                return ResponseEntity.ok(patchedComp.get());
            }
        }
        throw new CompanyUpdateError("Unable To Update Company");
    }

    @PostMapping("companies/coupons/update")
    public ResponseEntity<Coupon> updateCoupon(@RequestParam long id,
                                               @RequestParam String title,
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
            throw new InvalidTokenException("Unable to update Coupon, the Token is Incorrect");
        }
        clientSession.access();

        Coupon coupon = new Coupon(id, title, startDate, endDate, category, amount, description, price, image);

        Optional<Coupon> optDbCoupon = compService.getCoupon(id);
        if (optDbCoupon.isPresent()) {
            coupon.setCompany(optDbCoupon.get().getCompany());
        }

        /*Update will throw exception if the id is zero*/
        Optional<Coupon> optCoupon = compService.updateCoupon(coupon);
        if (optCoupon.isPresent()) {
            return ResponseEntity.ok(optCoupon.get());
        }
        throw new CouponDataError("Unable to update Coupon, check your inputs");
    }

    @GetMapping("companies/coupon")
    public ResponseEntity<Coupon> getCoupon(@RequestParam long id, @RequestParam String token) throws InvalidTokenException, CouponDataError {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupon, the Token is Invalid");
        }
        clientSession.access();

        Optional<Coupon> optCoupon = compService.getCoupon(id);

        if (optCoupon.isPresent()) {
            // A Company can get only coupons that belong to it
            boolean verifyCompId = optCoupon.get().getCompany().getId() == clientSession.getClientId();
            if (verifyCompId) {
                return ResponseEntity.ok(optCoupon.get());
            }
        }
        throw new CouponDataError("Unable to get Coupon, check your Inputs");
    }

    @GetMapping("companies/coupons/before")
    public ResponseEntity<List<Coupon>> getCouponsBeforeEndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, @RequestParam String token) throws InvalidTokenException, CouponDataError, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, The Token is Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> optCoupons =
                compService.getCompanyCouponsBeforeEndDate(endDate, clientSession.getClientId());

        if (optCoupons.isPresent()) {
            if (!optCoupons.get().isEmpty()) {
                return ResponseEntity.ok(optCoupons.get());
            }
        }
        throw new NoContentFoundException("No Content Found with given end date");
    }

    @GetMapping("companies/coupons/by-category")
    public ResponseEntity<List<Coupon>> getCompanyCouponsByCategory(@RequestParam int category, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, the Token is Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> optCouponList = compService.getCompanyCouponsByCategory(category, clientSession.getClientId());

        if (optCouponList.isPresent()) {
            if (!optCouponList.get().isEmpty()) {
                return ResponseEntity.ok(optCouponList.get());
            }
        }
        throw new NoContentFoundException("No Content found with given Category");
    }

    @GetMapping("/companies/coupons/by-price")
    public ResponseEntity<List<Coupon>> getCompanyCouponsLowerThenPrice(@RequestParam double price, @RequestParam String token) throws InvalidTokenException, NoContentFoundException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, the Token is Invalid");
        }
        clientSession.access();

        Optional<List<Coupon>> optCouponList = compService.getCompanyCouponLowerThanPrice(price);
        if (optCouponList.isPresent()) {
            List<Coupon> filteredList = optCouponList.get().stream()
                    .filter(coupon -> coupon.getCompany().getId() == clientSession.getClientId())
                    .collect(Collectors.toList());
            if (!filteredList.isEmpty()) {
                return ResponseEntity.ok(filteredList);
            }
        }
        throw new NoContentFoundException("No Coupons found with given Price");
    }


    /**
     * Function to help the frontend fetch the relevant entity after login
     */
    @GetMapping("/company")
    public ResponseEntity<Company> fetchCompanyByToken(@RequestParam String token) {
        ClientSession clientSession = tokenMap.get(token);
        long clientId = clientSession.getClientId();
        Optional<Company> companyById = compService.getCompanyById(clientId);
        if (companyById.isPresent()) {
            return ResponseEntity.ok(companyById.get());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/companies/coupons-by-sales")
    public ResponseEntity<List<Coupon>> getCompanyCouponsSortedBySales(@RequestParam String token) throws InvalidTokenException {
        ClientSession clientSession = tokenMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("Unable to get Coupons, the Token is Invalid");
        }
        Optional<List<Coupon>> optSortedCoupons = compService.sortCouponsBySalesAmount(clientSession.getClientId());
        if (optSortedCoupons.isPresent()) {
            return ResponseEntity.ok(optSortedCoupons.get());
        }
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("companies/logout")
    public ResponseEntity<Long> logout(@RequestParam String token) {
        if (tokenMap.get(token) != null) {
            return ResponseEntity.ok(tokenMap.remove(token).getClientId());
        }
        return ResponseEntity.noContent().build();
    }
}
