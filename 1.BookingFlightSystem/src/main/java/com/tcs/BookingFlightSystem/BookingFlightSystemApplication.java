package com.tcs.BookingFlightSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookingFlightSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingFlightSystemApplication.class, args);
	}

}
