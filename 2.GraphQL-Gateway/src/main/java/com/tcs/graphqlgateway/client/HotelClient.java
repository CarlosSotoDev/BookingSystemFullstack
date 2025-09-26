package com.tcs.graphqlgateway.client;

import com.tcs.graphqlgateway.dto.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "HotelService")
public interface HotelClient {

    @GetMapping("/api/v1/hotels")
    List<HotelDTO> getAll();

    @GetMapping("/api/v1/hotels/city/{city}")
    List<HotelDTO> byCity(@PathVariable("city") String city);

    @GetMapping("/api/v1/hotels/ppn/{pricePerNight}")
    HotelDTO byPrice(@PathVariable("pricePerNight") BigDecimal pricePerNight);
}
