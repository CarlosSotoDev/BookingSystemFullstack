package com.tcs.Promotions.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data //Import Getters and setters from lombook
@Builder // Best use of data
public class HotelDTO {
    private int id;
    private String hotelName;
    private String city;
    private LocalDate checkinDate;
    private BigDecimal pricePerNight;



}
