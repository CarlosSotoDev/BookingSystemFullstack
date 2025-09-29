package com.tcs.BookingFlightSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "flights")
@Data //@Data means thath you ara adding getters and setters with lombook dependency to all data inside
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@Getter @Setter this is another form to use Loombok focusen on an especific elements
    private String cityOrigin;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private BigDecimal price;
}
