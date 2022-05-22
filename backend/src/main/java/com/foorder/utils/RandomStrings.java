package com.foorder.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RandomStrings {

    private static StringBuilder getRandomString(int len) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" + "0123456789";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb;
    }

    private static String generateCurrDateTimeString(){
        LocalDateTime d = LocalDateTime.now();
        int hashCode = Math.abs(d.hashCode());
        return Integer.toString(hashCode);
    }

    public static int getRandomInt(int rangeMax){
        Random r = new Random();
        short randomValue = (short) ((short) r.nextInt((short)rangeMax + 1) );
        return (int) randomValue;
    }

    public static double getRandomDouble(int rangeMin, int rangeMax){
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    public static Long getRandomLong(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return Long.parseLong(String.format("%06d", number));
    }

    public static String getRandomUserName(){
        int len = 7;
        StringBuilder sb = new StringBuilder("U").append(getRandomString(len));
        return sb.toString();
    }

    public static String generateOrderId(){
        return "O" + generateCurrDateTimeString();
    }

    public static String generateRestaurantId(){
        int len = 7;
        StringBuilder sb = new StringBuilder("R").append(getRandomString(len));
        return sb.toString();
    }

    public static String generateMenuItemId(){
        int len = 3;
        StringBuilder sb = new StringBuilder("MI").append(getRandomString(len));
        return sb.toString();
    }

    public static Long generatePincode(){
        return getRandomLong();
    }
}
