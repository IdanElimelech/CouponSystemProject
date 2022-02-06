package com.jb.couponsystem.controller;

import com.jb.couponsystem.controller.ex.abs_ex.InternalServerErrorException;
import com.jb.couponsystem.controller.ex.abs_ex.UnauthorizedException;
import com.jb.couponsystem.model.ServerErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponSystemControllerAdvice {

    /**
     * Handle Exceptions with Status Unauthorized (catch all exceptions that Extends UnauthorizedException)
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerErrorResponse handleUnauthorized(UnauthorizedException ex) {
        return ServerErrorResponse.ofNow(ex.getMessage());
    }

    /**
     * Handle Exceptions with Status Internal Server Error (catch all exceptions that Extends InternalServerErrorException)
     * @param ex
     * @return
     */
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServerErrorResponse handleNoContent(InternalServerErrorException ex) {
        return ServerErrorResponse.ofNow(ex.getMessage());
    }


}
