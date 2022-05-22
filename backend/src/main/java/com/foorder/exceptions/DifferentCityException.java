package com.foorder.exceptions;

public class DifferentCityException extends Exception{

    @Override
    public String getMessage() {
        return "Cannot place order. Restaurant and User cities are different";
    }
}
