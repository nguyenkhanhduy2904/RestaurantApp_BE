package com.nguyenkhanhduy.restaurant_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
@EnableAsync
@SpringBootApplication
public class RestaurantAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantAppApplication.class, args);
//		System.out.println("ENV = " + System.getenv("FIREBASE_CONFIG_JSON"));
	}



}
