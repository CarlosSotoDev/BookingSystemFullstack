package com.tcs.graphqlgateway.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

//Se coupa record para traer clases inmutables
/*A todos los atributos les agrega el final
* Agrega constructor con todos los campos
* Getters(Estilo id(), nogetId)
* equals(), hashcode() y to string
* no genera setters, es ideal para los DTO*/
public record HotelDTO(
        Long id,
        String hotelName,
        String city,
        LocalDate checkingDate,
        BigDecimal pricePerNight
) { }
