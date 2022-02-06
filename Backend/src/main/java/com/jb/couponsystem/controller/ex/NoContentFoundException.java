package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.InternalServerErrorException;

public class NoContentFoundException extends InternalServerErrorException {
    public NoContentFoundException(String msg) {
        super(msg);
    }
}
