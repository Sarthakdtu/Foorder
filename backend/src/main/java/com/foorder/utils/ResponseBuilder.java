package com.foorder.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    public static ResponseEntity<?> buildGetResponse(Object object){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(object, headers, HttpStatus.OK);
    }

    public static ResponseEntity<?> buildPostResponse(String message){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> response = new HashMap<>();
        response.put("message", message + " inserted successfully.");
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<?> buildDeleteResponse(String message){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> response = new HashMap<>();
        response.put("message", message + " deleted successfully.");
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<?> buildErrorResponse(String reason, boolean serverError){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> response = new HashMap<>();
        response.put("message", reason);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(serverError){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response, headers, status);
    }
}
