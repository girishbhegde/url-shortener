package com.java.urlshortener.tools;

/**
 * Created by ghegde on 6/25/17.
 */
public class Base62Converter {
    private static String BASE_62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * This method gives a random char from base62 chars
     * @return
     */
    public char getRandomChar(){
        return BASE_62.charAt((int)(Math.random()* BASE_62.length() - 1));
    }
}
