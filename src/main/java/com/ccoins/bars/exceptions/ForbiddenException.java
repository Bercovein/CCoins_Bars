package com.ccoins.bars.exceptions;

public class ForbiddenException extends CustomException {

    public ForbiddenException(String code, String message){
        super(code,message);
    }
}
