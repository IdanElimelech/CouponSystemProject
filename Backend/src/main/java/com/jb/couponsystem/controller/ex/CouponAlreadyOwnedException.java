package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.UnauthorizedException;

public class CouponAlreadyOwnedException extends UnauthorizedException {
    public CouponAlreadyOwnedException(String msg) {
        super(msg);
    }
}
