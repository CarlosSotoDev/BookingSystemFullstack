package com.tcs.Promotions.client;

import com.tcs.Promotions.dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient (name ="fligthservice")
public interface FlightClient {
    @GetMapping("/api/v1/flights")
    List<FlightDTO> getAllFlights();
}
