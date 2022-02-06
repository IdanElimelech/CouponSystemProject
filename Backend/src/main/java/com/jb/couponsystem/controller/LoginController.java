package com.jb.couponsystem.controller;

import com.jb.couponsystem.model.ClientSession;
import com.jb.couponsystem.model.CouponSystem;
import com.jb.couponsystem.controller.ex.InvalidLoginException;
import com.jb.couponsystem.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class LoginController {
    private static final int LENGTH_TOKEN = 15;
    private final Map<String, ClientSession> tokensMap;
    private final CouponSystem couponSystem;

    @Autowired
    public LoginController(@Qualifier("tokens") Map<String, ClientSession> tokensMap, CouponSystem couponSystem) {
        this.tokensMap = tokensMap;
        this.couponSystem = couponSystem;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestParam String email, @RequestParam String password, @RequestParam int logType) throws InvalidLoginException {
        // Company can log as customer - fix this
        ClientSession session = couponSystem.createSession(email, password, logType);
        if (session != null) {
            String token = generateToken();

            tokensMap.put(token, session);

            return ResponseEntity.ok(new Token(token));
        }
        return ResponseEntity.noContent().build();
    }

    private String generateToken() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "")
                .substring(0, LENGTH_TOKEN);


    }

    public Map<String, ClientSession> getTokensMap() {
        return tokensMap;
    }
}
