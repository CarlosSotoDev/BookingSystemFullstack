package com.tcs.Promotions.client;

import com.tcs.Promotions.dto.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name  = "hotelservice")
public interface HotelClient {
    @GetMapping("/api/v1/hotels")
    List<HotelDTO> getAllHotels();
}
