package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.InternalServerErrorException;

public class CouponDataError extends InternalServerErrorException {
    public CouponDataError(String msg) {
        super(msg);
    }
}
