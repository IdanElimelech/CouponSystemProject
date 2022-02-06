package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.InternalServerErrorException;

public class CustomerDataError extends InternalServerErrorException {
    public CustomerDataError(String msg) {
        super(msg);
    }
}
