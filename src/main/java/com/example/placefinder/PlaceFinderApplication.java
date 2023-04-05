package com.example.placefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class PlaceFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaceFinderApplication.class, args);
    }

}
