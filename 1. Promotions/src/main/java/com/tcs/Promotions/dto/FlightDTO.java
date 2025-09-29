package com.tcs.Promotions.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data //Add getters and setter
@Builder  //Best use of data
public class FlightDTO {
    private String cityOrigin;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private BigDecimal price;
}
