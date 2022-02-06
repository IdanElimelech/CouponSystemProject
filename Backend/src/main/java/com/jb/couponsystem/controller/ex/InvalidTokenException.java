package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
    public InvalidTokenException(String msg) {
        super(msg);
    }
}
