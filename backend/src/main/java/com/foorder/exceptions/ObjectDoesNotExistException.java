package com.foorder.exceptions;

public class ObjectDoesNotExistException extends Exception{

    @Override
    public String getMessage() {
        return "does not exists.";
    }
}
