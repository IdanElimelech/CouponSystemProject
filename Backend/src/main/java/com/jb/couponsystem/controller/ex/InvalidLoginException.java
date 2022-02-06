package com.jb.couponsystem.controller.ex;

import com.jb.couponsystem.controller.ex.abs_ex.UnauthorizedException;

public class InvalidLoginException extends UnauthorizedException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
