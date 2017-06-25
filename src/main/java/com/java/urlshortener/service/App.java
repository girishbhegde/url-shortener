package com.java.urlshortener.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ghegde on 6/25/17.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.java.urlshortener")
public class App {
    /**
     * This starts the spring boot application
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
