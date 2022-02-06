package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.InternalServerErrorException;

public class CompanyDataError extends InternalServerErrorException {
    public CompanyDataError(String msg) {
        super(msg);
    }
}
