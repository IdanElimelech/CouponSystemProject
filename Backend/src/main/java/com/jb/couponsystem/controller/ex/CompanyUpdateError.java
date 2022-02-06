package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.InternalServerErrorException;

public class CompanyUpdateError extends InternalServerErrorException {
    public CompanyUpdateError(String msg) {
        super(msg);
    }
}
