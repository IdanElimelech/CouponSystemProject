package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.UnauthorizedException;

public class CouponAmountZero extends UnauthorizedException {
    public CouponAmountZero(String msg) {
        super(msg);
    }
}
