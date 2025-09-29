package com.tcs.graphqlgateway.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Hotel(
        Long id,
        String hotelName,
        String city,
        LocalDate checkingDate,
        BigDecimal pricePerNight
) {
    public static Hotel from(HotelDTO d){
        return new Hotel(d.id(), d.hotelName(), d.city(), d.checkingDate(), d.pricePerNight());
    }
}
