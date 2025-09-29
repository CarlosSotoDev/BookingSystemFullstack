package com.tcs.Promotions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PromotionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromotionsApplication.class, args);
	}

}
